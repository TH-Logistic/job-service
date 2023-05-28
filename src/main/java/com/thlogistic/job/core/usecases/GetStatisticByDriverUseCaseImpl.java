package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;
import com.thlogistic.job.adapters.dtos.statistic.JobStatisticDto;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.utils.Const;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetStatisticByDriverUseCaseImpl implements GetStatisticByDriverUseCase {
    private final DriverJobRepository driverJobRepository;
    private final JobProductRepository jobProductRepository;
    private final RouteClient routeClient;
    private final ProductClient productClient;

    @Override
    public GetJobStatisticResponse<GetJobListResponse> execute(BaseTokenRequest<String> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String driverId = baseTokenRequest.getRequestContent();

        List<DriverJobEntity> driverJobEntityList = driverJobRepository.findByDriverId(driverId);

        Double totalWeight = 0.0;
        Integer totalTonBasedJob = 0;
        Integer totalTripBasedJob = 0;
        Set<JobEntity> jobEntities = new HashSet<>();

        // Response
        GetJobStatisticResponse<GetJobListResponse> response = new GetJobStatisticResponse<>();
        List<GetJobListResponse> jobPagingDto = new LinkedList<>();
        JobStatisticDto jobStatisticDto = new JobStatisticDto();

        for (DriverJobEntity driverJobEntity : driverJobEntityList) {
            for (JobProductEntity jobProductEntity : driverJobEntity.getJob().getJobProductList()) {
                totalWeight += jobProductEntity.getWeight();
            }
            jobEntities.add(driverJobEntity.getJob());
        }

        for (JobEntity jobEntity : jobEntities) {
            if (jobEntity.getIsTonBased()) {
                totalTonBasedJob++;
            } else {
                totalTripBasedJob++;
            }

            GetJobListResponse.GetJobListResponseBuilder responseBuilder = GetJobListResponse.builder();
            getJobInfo(responseBuilder, jobEntity);
            getProductInfo(responseBuilder, jobEntity, token);
            getRouteInfo(responseBuilder, jobEntity, token);
            jobPagingDto.add(responseBuilder.build());
        }

        // Response
        jobStatisticDto.setTotalTripBasedJob(totalTripBasedJob);
        jobStatisticDto.setTotalTonBasedJob(totalTonBasedJob);
        // TODO: total distance
        jobStatisticDto.setTotalDistance(null);
        jobStatisticDto.setTotalWeight(totalWeight);

        response.setStatistic(jobStatisticDto);
        response.setJobs(jobPagingDto);

        return response;
    }

    private void getJobInfo(
            GetJobListResponse.GetJobListResponseBuilder builder,
            JobEntity jobEntity
    ) {
        builder.id(jobEntity.getJobId());
        builder.createdAt(jobEntity.getCreatedAt());

        builder.orderFee(jobEntity.getTotalPrice());
        builder.status(jobEntity.getStatus());
    }

    private void getRouteInfo(
            GetJobListResponse.GetJobListResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        try {
            BaseResponse<GetRouteDto> response = routeClient.getRoute(authToken, jobEntity.getRouteId());
            builder.pickUpAt(response.getData().getFromLocation().getName());
            builder.unloadAt(response.getData().getToLocation().getName());
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading route");
        }
    }

    private void getProductInfo(
            GetJobListResponse.GetJobListResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        List<JobProductEntity> jobProductEntityList = jobProductRepository.findAllByJobId(jobEntity.getJobId());
        List<String> productIds = jobProductEntityList.stream().map(e -> e.getJobProductKey().getProductId()).toList();

        try {
            BaseResponse<List<GetProductDto>> response = productClient.findAllProductsByIds(
                    authToken,
                    String.join(", ", productIds)
            );
            builder.products(response.getData().stream().map(GetProductDto::getName).toList());
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading products");
        }
    }
}
