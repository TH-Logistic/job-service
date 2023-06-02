package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.CreateHealthcheckRequest;
import com.thlogistic.job.adapters.dtos.UpdateJobStatusRequest;
import com.thlogistic.job.aop.exception.BadRequestException;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.healthcheck.CreateHealthcheckClientRequest;
import com.thlogistic.job.client.healthcheck.HealthcheckClient;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.client.transportation.UpdateTransportationDeliveryStatusRequest;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.utils.DateTimeHelper;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateJobStatusUseCaseImpl implements UpdateJobStatusUseCase {
    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final HealthcheckClient healthcheckClient;
    private final TransportationClient transportationClient;

    @Override
    public Boolean execute(BaseTokenRequest<Pair<String, UpdateJobStatusRequest>> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String jobId = baseTokenRequest.getRequestContent().getFirst();

        Optional<JobEntity> jobEntityOptional = jobRepository.findById(jobId);
        if (jobEntityOptional.isEmpty()) {
            throw new DataNotFoundException("Job");
        }
        JobEntity jobEntity = jobEntityOptional.get();
        Integer currentJobStatusCode = jobEntity.getStatus();

        UpdateJobStatusRequest requestContent = baseTokenRequest.getRequestContent().getSecond();
        Integer requestJobStatus = requestContent.getJobStatus();

        if (requestJobStatus <= currentJobStatusCode) {
            throw new BadRequestException("Invalid job status");
        }

        switch (JobStatus.fromInt(requestJobStatus)) {
            case JOB_STARTED -> {
                if (!currentJobStatusCode.equals(JobStatus.ASSIGNED.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }

                if (requestContent.getHealthcheck() == null ||
                        !Objects.equals(jobId, jobEntity.getJobId())
                ) {
                    throw new BadRequestException("Invalid healthcheck");
                } else {
                    try {
                        CreateHealthcheckRequest healthcheck = requestContent.getHealthcheck();
                        CreateHealthcheckClientRequest clientRequestBody = new CreateHealthcheckClientRequest(
                                jobId,
                                healthcheck.getIsTiresOk(),
                                healthcheck.getIsLightOk(),
                                healthcheck.getIsBrakeOk(),
                                healthcheck.getIsFluidLevelOk(),
                                healthcheck.getIsBatteryOk(),
                                healthcheck.getIsWiperOk(),
                                healthcheck.getNote()
                        );
                        healthcheckClient.createHealthcheck(token, clientRequestBody);
                    } catch (Exception e) {
                        throw new CustomRuntimeException("An error occurred when update healthcheck");
                    }

                    String driverId = driverJobRepository.findByJobId(jobId).get(0).getDriverId();
                    String transportationId = getTransportationId(token, driverId);

                    UpdateTransportationDeliveryStatusRequest body =
                            new UpdateTransportationDeliveryStatusRequest(2, null);
                    updateTransportationStatus(token, transportationId, body);

                    jobEntity.setStatus(JobStatus.JOB_STARTED.statusCode);
                    jobEntity.setStartedAt(DateTimeHelper.getCurrentTimeInEpoch());
                }
            }
            case PICK_UP_ARRIVE -> {
                if (!currentJobStatusCode.equals(JobStatus.JOB_STARTED.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.PICK_UP_ARRIVE.statusCode);
                jobEntity.setPickUpArriveAt(DateTimeHelper.getCurrentTimeInEpoch());
            }
            case PICK_UP_DONE -> {
                if (!currentJobStatusCode.equals(JobStatus.PICK_UP_ARRIVE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.PICK_UP_DONE.statusCode);
                jobEntity.setPickUpDoneAt(DateTimeHelper.getCurrentTimeInEpoch());
            }
            case DELIVERY_ARRIVE -> {
                if (!currentJobStatusCode.equals(JobStatus.PICK_UP_DONE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.DELIVERY_ARRIVE.statusCode);
                jobEntity.setDeliveryArriveAt(DateTimeHelper.getCurrentTimeInEpoch());
            }
            case DISCHARGED -> {
                if (!currentJobStatusCode.equals(JobStatus.DELIVERY_ARRIVE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.DISCHARGED.statusCode);
                jobEntity.setDischargedAt(DateTimeHelper.getCurrentTimeInEpoch());
            }
            case COMPLETED -> {
                if (!currentJobStatusCode.equals(JobStatus.DISCHARGED.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.COMPLETED.statusCode);
                jobEntity.setCompletedAt(DateTimeHelper.getCurrentTimeInEpoch());

                String driverId = driverJobRepository.findByJobId(jobId).get(0).getDriverId();
                String transportationId = getTransportationId(token, driverId);

                UpdateTransportationDeliveryStatusRequest body =
                        new UpdateTransportationDeliveryStatusRequest(1, jobEntity.getEndingGarageId());
                updateTransportationStatus(token, transportationId, body);
            }
            default -> {
                throw new BadRequestException("An error occurred when updating job status");
            }
        }
        jobRepository.save(jobEntity);
        return true;
    }

    private String getTransportationId(String authToken, String driverId) {
        try {
            BaseResponse<GetTransportationDto> getTransportationResponse =
                    transportationClient.getTransportationByDriverId(authToken, driverId);
            return getTransportationResponse.getData().getId();
        } catch (Exception e) {
            throw new CustomRuntimeException("An error occurred when loading transportation");
        }
    }

    private void updateTransportationStatus(String authToken, String transportationId, UpdateTransportationDeliveryStatusRequest body) {
        try {
            System.out.println("justin transportationId: " + transportationId);
            transportationClient.updateDeliveryStatus(authToken, transportationId, body);
        } catch (Exception e) {
            System.out.println("justin: " + e.getMessage());
            throw new CustomRuntimeException("An error occurred when update transportation status");
        }
    }
}
