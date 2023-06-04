package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.healthcheck.GetHealthcheckClientResponse;
import com.thlogistic.job.client.healthcheck.HealthcheckClient;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.client.transportation.GetGarageDto;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetJobUseCaseImpl implements GetJobUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final JobProductRepository jobProductRepository;
    private final GetNumberOfTripsOfDriverUseCase getNumberOfTripsOfDriverUseCase;
    private final ProductClient productClient;
    private final RouteClient routeClient;
    private final TransportationClient transportationClient;
    private final HealthcheckClient healthcheckClient;

    @Override
    public GetJobResponse execute(BaseTokenRequest<String> baseTokenRequest) {
        String jobId = baseTokenRequest.getRequestContent();
        Optional<JobEntity> jobEntityOptional = jobRepository.findById(jobId);
        if (jobEntityOptional.isEmpty()) {
            throw new DataNotFoundException("Job");
        }
        JobEntity jobEntity = jobEntityOptional.get();

        GetJobResponse.GetJobResponseBuilder responseBuilder = GetJobResponse.builder();

        // Get data
        buildJobResponse(responseBuilder, jobEntity);
        getProductResponse(responseBuilder, jobEntity, baseTokenRequest.getToken());
        getRouteResponse(responseBuilder, jobEntity, baseTokenRequest.getToken());
        getTransportationResponse(responseBuilder, jobEntity, baseTokenRequest.getToken());
        getGarageResponse(responseBuilder, jobEntity, baseTokenRequest.getToken());
        getHealthcheckResponse(responseBuilder, jobEntity, baseTokenRequest.getToken());

        return responseBuilder.build();
    }

    private void buildJobResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity
    ) {
        builder.id(jobEntity.getJobId());
        builder.status(jobEntity.getStatus());
        builder.totalPrice(jobEntity.getTotalPrice());
        builder.isTonBased(jobEntity.getIsTonBased());
        builder.mustDeliverAt(jobEntity.getMustDeliverAt());
        builder.createdAt(jobEntity.getCreatedAt());
        builder.assignedAt(jobEntity.getAssignedAt());
        builder.acceptedAt(jobEntity.getStartedAt());
        builder.pickUpArriveAt(jobEntity.getPickUpArriveAt());
        builder.pickUpDoneAt(jobEntity.getPickUpDoneAt());
        builder.unloadArriveAt(jobEntity.getDeliveryArriveAt());
        builder.unloadDoneAt(jobEntity.getDischargedAt());
        builder.completedAt(jobEntity.getCompletedAt());
        builder.pickUpContactName(jobEntity.getPickUpContactName());
        builder.pickUpContactNo(jobEntity.getPickUpContactNo());
        builder.unloadContactName(jobEntity.getUnloadContactName());
        builder.unloadContactNo(jobEntity.getUnloadContactNo());
        builder.notesToDriver(jobEntity.getNotesToDriver());
    }

    private void getProductResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        List<JobProductEntity> jobProductEntityList = jobProductRepository.findAllByJobId(jobEntity.getJobId());
        List<String> productIds = jobProductEntityList.stream().map(e -> e.getJobProductKey().getProductId()).toList();

        try {
            BaseResponse<List<GetProductDto>> response = productClient.findAllProductsByIds(
                    authToken,
                    String.join(",", productIds)
            );
            builder.products(response.getData());
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading products");
        }
    }

    private void getTransportationResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        List<DriverJobEntity> driverJobEntities = driverJobRepository.findByJobId(
                jobEntity.getJobId()
        );
        if (driverJobEntities.isEmpty()) {
            builder.transportation(null);
            return;
        }
        DriverJobEntity driverJobEntity = driverJobEntities.get(0);

        String driverId = driverJobEntity.getDriverId();
        try {
            BaseResponse<GetTransportationDto> getTransportationResponse =
                    transportationClient.getTransportationByDriverId(authToken, driverId);
            builder.transportation(getTransportationResponse.getData());
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading transportation");
        }
    }

    private void getGarageResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        // Starting garage
        if (jobEntity.getStatus() >= JobStatus.ASSIGNED.statusCode && jobEntity.getStartingGarageId() != null) {
            try {
                BaseResponse<GetGarageDto> getGarageResponse = transportationClient.getGarage(authToken, jobEntity.getStartingGarageId());
                builder.startingGarage(getGarageResponse.getData());
            } catch (Exception e) {
                throw new CustomRuntimeException("An error occurred when loading starting garage");
            }
        } else {
            builder.startingGarage(null);
        }

        // Ending garage
        if (jobEntity.getEndingGarageId() != null) {
            try {
                BaseResponse<GetGarageDto> getGarageResponse = transportationClient.getGarage(authToken, jobEntity.getEndingGarageId());
                builder.endingGarage(getGarageResponse.getData());
            } catch (Exception e) {
                throw new CustomRuntimeException("An error occurred when loading ending garage");
            }
        } else {
            builder.endingGarage(null);
        }
    }

    private void getRouteResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        try {
            BaseResponse<GetRouteDto> response = routeClient.getRoute(authToken, jobEntity.getRouteId());
            builder.route(response.getData());
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading route");
        }
    }

    private void getHealthcheckResponse(
            GetJobResponse.GetJobResponseBuilder builder,
            JobEntity jobEntity,
            String authToken
    ) {
        if (jobEntity.getStatus() >= JobStatus.JOB_STARTED.statusCode) {
            try {
                BaseResponse<GetHealthcheckClientResponse> getHealthcheckResponse =
                        healthcheckClient.getHealthcheck(authToken, jobEntity.getJobId());
                builder.healthcheck(getHealthcheckResponse.getData());
            } catch (Exception e) {
                throw new CustomRuntimeException("An error occurred when loading healthcheck");
            }
        } else {
            builder.healthcheck(null);
        }
    }
}
