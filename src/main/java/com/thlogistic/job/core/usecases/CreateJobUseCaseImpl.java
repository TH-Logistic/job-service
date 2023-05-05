package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.aop.exception.InvalidDateTimeFormatException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateJobUseCaseImpl implements CreateJobUseCase {

    private final JobRepository jobRepository;
    private final JobProductRepository jobProductRepository;
    private final ProductClient productClient;
    private final RouteClient routeClient;

    @Override
    public CreateJobResponse execute(BaseTokenRequest<CreateJobRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        CreateJobRequest requestContent = baseTokenRequest.getRequestContent();

        List<String> productIds = requestContent.getProductList().stream().map(
                ProductCreateJobDto::getProductId
        ).toList();
        checkIfProductIdListIsValid(token, productIds);

        String routeId = requestContent.getRouteId();
        checkIfRouteIdIsValid(token, routeId);

        checkIfDeliveryTimeIsValid(requestContent.getDeliveryTime());

        JobEntity jobEntity = JobEntity.builder()
                .driverId(null)
                .routeId(routeId)
                .status(JobStatus.OPEN.statusCode)
                .mustDeliverAt(requestContent.getDeliveryTime())
                .createdAt(DateTimeHelper.getCurrentTimeFormatted())
                .pickUpContactName(requestContent.getPickUpContactName())
                .pickUpContactNo(requestContent.getPickUpContactNo())
                .unloadContactName(requestContent.getUnloadContactName())
                .unloadContactNo(requestContent.getUnloadContactNo())
                .notesToDriver(requestContent.getNotesToDriver())
                .build();

        String jobEntityId = jobRepository.insert(jobEntity);

        requestContent.getProductList().forEach(dto -> {
            JobProductEntity jobProductEntity = JobProductEntity.builder()
                    .jobProductKey(
                            new JobProductKey(jobEntityId, dto.getProductId())
                    )
                    .weight(dto.getWeight())
                    .grandTotal(calculateGrandTotal(dto))
                    .job(jobEntity)
                    .build();
            jobProductRepository.insert(jobProductEntity);
        });
        return new CreateJobResponse(jobEntityId);
    }

    private void checkIfProductIdListIsValid(String token, List<String> productIds) {
        productIds.forEach(id -> {
            try {
                productClient.getProduct(token, id);
            } catch (Exception e) {
                throw new DataNotFoundException("Product");
            }
        });
    }

    private void checkIfRouteIdIsValid(String token, String routeId) {
        try {
            routeClient.getRoute(token, routeId);
        } catch (Exception e) {
            throw new DataNotFoundException("Route");
        }
    }

    private void checkIfDeliveryTimeIsValid(String time) {
        if (!DateTimeHelper.checkDateTimeFormat(time)) {
            throw new InvalidDateTimeFormatException("Invalid datetime format");
        }
    }

    private Double calculateGrandTotal(ProductCreateJobDto dto) {
        return dto.getGrandTotal();
    }
}
