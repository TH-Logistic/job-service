package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobPagingResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.client.healthcheck.HealthcheckClient;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetStatisticByProductUseCaseImpl implements GetStatisticByProductUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final JobProductRepository jobProductRepository;
    private final ProductClient productClient;
    private final RouteClient routeClient;
    private final TransportationClient transportationClient;
    private final HealthcheckClient healthcheckClient;

    @Override
    public GetJobStatisticResponse execute(BaseTokenRequest<String> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String productId = baseTokenRequest.getRequestContent();

        List<JobProductEntity> jobProductEntityList = jobProductRepository.findByProductId(productId);


        Double totalWeight = 0.0;
        Integer totalTonBasedJob = 0;
        Integer totalTripBasedJob = 0;
        Set<JobEntity> jobEntities = new HashSet<>();
        // TODO: total distance

        for (JobProductEntity jobProductEntity : jobProductEntityList) {
            totalWeight += jobProductEntity.getWeight();
            jobEntities.add(jobProductEntity.getJob());
        }
        for (JobEntity jobEntity : jobEntities) {
            if (jobEntity.getIsTonBased()) {
                totalTonBasedJob++;
            } else {
                totalTripBasedJob++;
            }

        }

        return null;
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
}