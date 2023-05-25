package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.CreateJobResponse;
import com.thlogistic.job.adapters.dtos.ProductCreateJobRequest;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.aop.exception.InvalidDateTimeFormatException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.core.entities.JobProduct;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateJobUseCaseImpl implements CreateJobUseCase {

    private final JobRepository jobRepository;
    private final JobProductRepository jobProductRepository;
    private final DriverJobRepository driverJobRepository;
    private final ProductClient productClient;
    private final RouteClient routeClient;

    @Override
    public CreateJobResponse execute(BaseTokenRequest<CreateJobRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        CreateJobRequest requestContent = baseTokenRequest.getRequestContent();

        List<String> productIds = requestContent.getProductList().stream().map(
                ProductCreateJobRequest::getProductId
        ).toList();

        List<GetProductDto> checkedProduct = checkIfProductIdListIsValid(token, productIds);

        // Map GetProductDto from Product Client and JobProductEntity into JobEntity
        List<JobProduct> jobProducts = getJobProducts(checkedProduct, requestContent.getProductList());

        String routeId = requestContent.getRouteId();
        GetRouteDto routeDto = checkIfRouteIdIsValid(token, routeId);

        // Calculate job price
        Double jobPrice;
        Boolean isTonBasedJob = isTonBasedJob(jobProducts, routeDto.getTonBasedLimit());
        if (isTonBasedJob) {
            jobPrice = calculateTotalProductPrice(jobProducts);
        } else {
            jobPrice = routeDto.getTripBasedCost();
        }

        checkIfDeliveryTimeIsValid(requestContent.getDeliveryTime());

        JobEntity jobEntity = JobEntity.builder()
                .routeId(routeId)
                .status(JobStatus.OPEN.statusCode)
                .totalPrice(jobPrice)
                .isTonBased(isTonBasedJob)
                .mustDeliverAt(requestContent.getDeliveryTime())
                .createdAt(DateTimeHelper.getCurrentTimeInEpoch())
                .pickUpContactName(requestContent.getPickUpContactName())
                .pickUpContactNo(requestContent.getPickUpContactNo())
                .unloadContactName(requestContent.getUnloadContactName())
                .unloadContactNo(requestContent.getUnloadContactNo())
                .notesToDriver(requestContent.getNotesToDriver())
                .build();

        String jobEntityId = jobRepository.insert(jobEntity);

        jobProducts.forEach(jobProduct -> {
            JobProductEntity jobProductEntity = JobProductEntity.builder()
                    .jobProductKey(
                            new JobProductKey(jobEntityId, jobProduct.getProductId())
                    )
                    .weight(jobProduct.getWeight())
                    .grandTotal(jobProduct.getGrandTotal())
                    .job(jobEntity)
                    .build();
            jobProductRepository.insert(jobProductEntity);
        });
        return new CreateJobResponse(jobEntityId);
    }

    private List<GetProductDto> checkIfProductIdListIsValid(String token, List<String> productIds) {
        try {
            return productClient.findAllProductsByIds(
                            token,
                            String.join(",", productIds))
                    .getData();
        } catch (Exception e) {
            throw new DataNotFoundException("Product");
        }
    }

    private GetRouteDto checkIfRouteIdIsValid(String token, String routeId) {
        try {
            return routeClient.getRoute(token, routeId).getData();
        } catch (Exception e) {
            throw new DataNotFoundException("Route");
        }
    }

    private void checkIfDeliveryTimeIsValid(Long time) {
        if (!DateTimeHelper.checkDateTimeFormat(time)) {
            throw new InvalidDateTimeFormatException("Invalid datetime");
        }
    }

    private List<JobProduct> getJobProducts(List<GetProductDto> checkedProduct, List<ProductCreateJobRequest> productsRequestContent) {
        List<JobProduct> jobProducts = new LinkedList<>();
        checkedProduct.forEach(p -> {
            Double productWeight = productsRequestContent.stream().filter(request -> {
                return Objects.equals(request.getProductId(), p.getId());
            }).findFirst().get().getWeight();
            JobProduct newJobProduct = new JobProduct(
                    p.getId(),
                    productWeight,
                    p.getUnit(),
                    p.getTypes(),
                    p.getBasePrice(),
                    p.getBasePrice() * productWeight
            );
            jobProducts.add(newJobProduct);
        });
        return jobProducts;
    }

    private Double calculateTotalProductPrice(List<JobProduct> jobProducts) {
        Double totalProductPrice = 0.0;

        for (JobProduct jobProduct : jobProducts) {
            totalProductPrice += jobProduct.getGrandTotal();
        }
        return totalProductPrice;
    }

    private boolean isTonBasedJob(List<JobProduct> jobProducts, Double tonBasedLimit) {
        Double totalWeight = 0.0;
        for (JobProduct jobProduct : jobProducts) {
            totalWeight += jobProduct.getWeight();
        }
        return totalWeight >= tonBasedLimit;
    }
}
