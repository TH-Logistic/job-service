package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobListResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;
import com.thlogistic.job.adapters.dtos.statistic.JobStatisticDto;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
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
public class GetStatisticByRouteUseCaseImpl implements GetStatisticByRouteUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final JobProductRepository jobProductRepository;
    private final TransportationClient transportationClient;
    private final ProductClient productClient;

    @Override
    public GetJobStatisticResponse<GetJobListResponse> execute(BaseTokenRequest<String> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String routeId = baseTokenRequest.getRequestContent();

        List<JobEntity> jobEntityList = jobRepository.findByRouteId(routeId);

        Double totalWeight = 0.0;
        Integer totalTonBasedJob = 0;
        Integer totalTripBasedJob = 0;

        // Response
        GetJobStatisticResponse<GetJobListResponse> response = new GetJobStatisticResponse<>();
        List<GetJobListResponse> jobPagingDto = new LinkedList<>();
        JobStatisticDto jobStatisticDto = new JobStatisticDto();

        for (JobEntity jobEntity : jobEntityList) {
            if (jobEntity.getIsTonBased()) {
                totalTonBasedJob++;
            } else {
                totalTripBasedJob++;
            }

            for (JobProductEntity jobProductEntity : jobEntity.getJobProductList()) {
                totalWeight += jobProductEntity.getWeight();
            }

            GetJobListResponse.GetJobListResponseBuilder responseBuilder = GetJobListResponse.builder();
            getJobInfo(responseBuilder, jobEntity);
            getProductInfo(responseBuilder, jobEntity, token);
            getTransportationInfo(responseBuilder, jobEntity, token);
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

    private void getTransportationInfo(
            GetJobListResponse.GetJobListResponseBuilder builder,
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
