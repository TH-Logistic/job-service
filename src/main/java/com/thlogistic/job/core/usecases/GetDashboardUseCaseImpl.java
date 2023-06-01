package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetDashboardRequest;
import com.thlogistic.job.adapters.dtos.dashboard.GetDashboardResponse;
import com.thlogistic.job.adapters.dtos.dashboard.GetLineChartItemDto;
import com.thlogistic.job.adapters.dtos.dashboard.GetOrderPricePieChartDto;
import com.thlogistic.job.adapters.dtos.dashboard.GetRecentJobDashboardDto;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.client.billing.BillingClient;
import com.thlogistic.job.client.billing.GetBillingStatisticResponse;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetDashboardUseCaseImpl implements GetDashboardUseCase {

    private final JobRepository jobRepository;
    private final JobProductRepository jobProductRepository;
    private final ProductClient productClient;
    private final BillingClient billingClient;
    private final TransportationClient transportationClient;
    private final RouteClient routeClient;
    private final GetJobTotalUseCase getJobTotalUseCase;

    @Override
    public GetDashboardResponse execute(BaseTokenRequest<GetDashboardRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        Integer year = baseTokenRequest.getRequestContent().getYear();

        List<JobEntity> recentJobEntities = jobRepository.findLastedJob();
        List<JobEntity> jobsByYearEntities = jobRepository.findAllJobInYear(year);
        List<JobEntity> allJobs = jobRepository.findAll();

        GetDashboardResponse response = new GetDashboardResponse();

        response.setTotalOrders(getTotalOrders());
        response.setTotalTrucks(getTotalTransportations(token));
        response.setTotalRoutes(getTotalRoute(token));
        response.setBilling(getBillingStatistic(token));
        response.setOrderPricePieChart(calculateJobPriceByType(allJobs));
        response.setLineChart(calculateJobPriceInMonths(jobsByYearEntities));
        response.setRecentJobs(getRecentJobsResponse(recentJobEntities, token));

        return response;
    }

    private Integer getTotalOrders() {
        return getJobTotalUseCase.execute();
    }

    private Integer getTotalTransportations(String token) {
        try {
            return transportationClient.getTotalTransportation(token).getData();
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading total transportations");
        }
    }

    private Integer getTotalRoute(String token) {
        try {
            return routeClient.getTotalRoutes(token).getData();
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading total routes");
        }
    }

    private GetOrderPricePieChartDto calculateJobPriceByType(List<JobEntity> entities) {
        Double tonBasedJobPrice = 0.0;
        Double tripBasedJobPrice = 0.0;
        for (JobEntity entity: entities) {
            if (entity.getIsTonBased()) {
                tonBasedJobPrice += entity.getTotalPrice();
            } else {
                tripBasedJobPrice += entity.getTotalPrice();
            }
        }
        return new GetOrderPricePieChartDto(tonBasedJobPrice, tripBasedJobPrice);
    }

    private List<GetRecentJobDashboardDto> getRecentJobsResponse(List<JobEntity> entities, String authToken) {
        List<GetRecentJobDashboardDto> result = new LinkedList<>();
        entities.forEach(entity -> {
            result.add(
                    new GetRecentJobDashboardDto(
                            entity.getJobId(),
                            getProductInfo(entity, authToken),
                            entity.getStatus(),
                            entity.getCreatedAt()
                    )
            );
        });
        return result;
    }

    private List<GetLineChartItemDto> calculateJobPriceInMonths(List<JobEntity> entities) {
        Map<String, Double> priceByMonthMap = new LinkedHashMap<>();

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (String month : months) {
            priceByMonthMap.put(month, 0.0);
        }

        for (JobEntity entity : entities) {
            // Format time to month string
            String monthString = DateTimeHelper.getMonthOnlyFromEpoch(entity.getCreatedAt());
            // Add the order price to the corresponding month group
            Double currentPrice = priceByMonthMap.get(monthString);
            priceByMonthMap.put(monthString, currentPrice += entity.getTotalPrice());
        }

        List<GetLineChartItemDto> result = new LinkedList<>();

        priceByMonthMap.forEach((month, totalPrice) -> result.add(
                new GetLineChartItemDto(month, totalPrice)
        ));

        return result;
    }

    private List<String> getProductInfo(JobEntity jobEntity, String authToken) {
        List<JobProductEntity> jobProductEntityList = jobProductRepository.findAllByJobId(jobEntity.getJobId());
        List<String> productIds = jobProductEntityList.stream().map(e -> e.getJobProductKey().getProductId()).toList();

        try {
            BaseResponse<List<GetProductDto>> response = productClient.findAllProductsByIds(
                    authToken,
                    String.join(", ", productIds)
            );
            return response.getData().stream().map(GetProductDto::getName).toList();
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading products");
        }
    }

    private GetBillingStatisticResponse getBillingStatistic(String authToken) {
        try {
            BaseResponse<GetBillingStatisticResponse> response = billingClient.getStatisticForDashboard(
                    authToken
            );
            return response.getData();
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading billings");
        }
    }
}
