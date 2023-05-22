package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
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
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetJobPagingUseCaseImpl implements GetJobPagingUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final JobProductRepository jobProductRepository;
    private final RouteClient routeClient;
    private final ProductClient productClient;
    private final TransportationClient transportationClient;

    @Override
    public BasePagingResponse<GetJobPagingResponse> execute(BaseTokenRequest<ListJobPagingRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        ListJobPagingRequest requestContent = baseTokenRequest.getRequestContent();

        BasePagingQueryResult<List<JobEntity>> queryResult = jobRepository.paging(
                requestContent.getKeyword(),
                requestContent.getStatusList(),
                requestContent.getMinOrderFee(),
                requestContent.getMaxOrderFee(),
                requestContent.getPage(),
                requestContent.getSize()
        );

        List<GetJobPagingResponse> responseContent = new LinkedList<>();
        queryResult.getData().forEach(entity -> {
            GetJobPagingResponse.GetJobPagingResponseBuilder responseBuilder = GetJobPagingResponse.builder();
            getJobInfo(responseBuilder, entity);
            getTransportationInfo(responseBuilder, entity, token);
            getRouteInfo(responseBuilder, entity, token);
            getProductInfo(responseBuilder, entity, token);

            responseContent.add(responseBuilder.build());
        });

        BasePagingResponse<GetJobPagingResponse> response = new BasePagingResponse<>();
        response.setContent(responseContent);
        response.setTotal(queryResult.getTotal());
        response.setTotalPage(queryResult.getTotalPage());

        return response;
    }

    private void getJobInfo(
            GetJobPagingResponse.GetJobPagingResponseBuilder builder,
            JobEntity jobEntity
    ) {
        builder.id(jobEntity.getJobId());
        builder.createdAt(jobEntity.getCreatedAt());

        String pickUpAt = jobEntity.getPickUpDoneAt();
        if (pickUpAt == null || pickUpAt.isEmpty()) {
            builder.pickUpAt(Const.Job.NOT_YET);
        } else {
            builder.pickUpAt(pickUpAt);
        }

        String unloadAt = jobEntity.getDischargedAt();
        if (unloadAt == null || unloadAt.isEmpty()) {
            builder.unloadAt(Const.Job.NOT_YET);
        } else {
            builder.unloadAt(unloadAt);
        }

        builder.orderFee(jobEntity.getTotalPrice());
        builder.status(jobEntity.getStatus());
    }

    private void getTransportationInfo(
            GetJobPagingResponse.GetJobPagingResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        if (jobEntity.getStatus() >= JobStatus.ASSIGNED.statusCode) {
            List<DriverJobEntity> driverJobEntities = driverJobRepository.findByJobId(
                    jobEntity.getJobId()
            );
            if (driverJobEntities.isEmpty()) {
                throw new CustomRuntimeException("An error occurred when loading driver");
            }
            DriverJobEntity driverJobEntity = driverJobEntities.get(0);

            String driverId = driverJobEntity.getDriverId();
            try {
                BaseResponse<GetTransportationDto> getTransportationResponse =
                        transportationClient.getTransportationByDriverId(authToken, driverId);
                builder.licensePlate(getTransportationResponse.getData().getLicensePlate());
                builder.driverInCharge(getTransportationResponse.getData().getMainDriver().getName());
            } catch (Exception e) {
                throw new CustomRuntimeException("An error occurred when loading transportation");
            }
        } else {
            builder.licensePlate(Const.Job.NOT_ASSIGNED);
            builder.driverInCharge(Const.Job.NOT_ASSIGNED);
        }
    }

    private void getRouteInfo(
            GetJobPagingResponse.GetJobPagingResponseBuilder builder,
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
            GetJobPagingResponse.GetJobPagingResponseBuilder builder,
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
