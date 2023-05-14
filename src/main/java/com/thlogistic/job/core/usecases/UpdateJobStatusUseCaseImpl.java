package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.aop.exception.BadRequestException;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.healthcheck.HealthcheckClient;
import com.thlogistic.job.core.entities.JobStatus;
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
    private final HealthcheckClient healthcheckClient;

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

                if (jobEntity.getEndingGarageId() == null) {
                    throw new BadRequestException("Cannot start job without ending garage");
                }

                jobEntity.setStatus(JobStatus.JOB_STARTED.statusCode);
                jobEntity.setStartedAt(DateTimeHelper.getCurrentTimeFormatted());
                if (requestContent.getHealthcheck() == null ||
                        !Objects.equals(requestContent.getHealthcheck().getJobId(), jobEntity.getJobId())
                ) {
                    throw new BadRequestException("Invalid healthcheck");
                } else {
                    try {
                        healthcheckClient.createHealthcheck(token, requestContent.getHealthcheck());
                    } catch (Exception e) {
                        System.out.println("justin token: " + token);
                        System.out.println("justin exception: " + e.getMessage());
                        throw new CustomRuntimeException("An error occurred when update healthcheck");
                    }
                }
            }
            case PICK_UP_ARRIVE -> {
                if (!currentJobStatusCode.equals(JobStatus.JOB_STARTED.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.PICK_UP_ARRIVE.statusCode);
                jobEntity.setPickUpArriveAt(DateTimeHelper.getCurrentTimeFormatted());
            }
            case PICK_UP_DONE -> {
                if (!currentJobStatusCode.equals(JobStatus.PICK_UP_ARRIVE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.PICK_UP_DONE.statusCode);
                jobEntity.setPickUpDoneAt(DateTimeHelper.getCurrentTimeFormatted());
            }
            case DELIVERY_ARRIVE -> {
                if (!currentJobStatusCode.equals(JobStatus.PICK_UP_DONE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.DELIVERY_ARRIVE.statusCode);
                jobEntity.setDeliveryArriveAt(DateTimeHelper.getCurrentTimeFormatted());
            }
            case DISCHARGED -> {
                if (!currentJobStatusCode.equals(JobStatus.DELIVERY_ARRIVE.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.DISCHARGED.statusCode);
                jobEntity.setDischargedAt(DateTimeHelper.getCurrentTimeFormatted());
            }
            case COMPLETED -> {
                if (!currentJobStatusCode.equals(JobStatus.DISCHARGED.statusCode)) {
                    throw new BadRequestException("Invalid job status");
                }
                jobEntity.setStatus(JobStatus.COMPLETED.statusCode);
                jobEntity.setCompletedAt(DateTimeHelper.getCurrentTimeFormatted());
            }
            default -> {
                throw new BadRequestException("An error occurred when updating job status");
            }
        }
        jobRepository.save(jobEntity);
        return true;
    }
}
