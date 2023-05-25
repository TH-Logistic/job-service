package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.AddEndingGarageRequest;
import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.transportation.GetGarageDto;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddEndingGarageUseCaseImpl implements AddEndingGarageUseCase {

    private final JobRepository jobRepository;
    private final TransportationClient transportationClient;

    @Override
    public Boolean execute(BaseTokenRequest<AddEndingGarageRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        AddEndingGarageRequest request = baseTokenRequest.getRequestContent();

        BaseResponse<GetGarageDto> getGarageResponse;

        // Check valid transportation Id
        try {
            getGarageResponse = transportationClient.getGarage(token, request.getEndingGarageId());
        } catch (Exception e) {
            throw new DataNotFoundException("Transportation");
        }

        // Check valid job Id
        Optional<JobEntity> jobEntityOptional = jobRepository.findById(request.getJobId());
        if (jobEntityOptional.isEmpty()) {
            throw new DataNotFoundException("Job");
        }

        GetGarageDto getGarageDto = getGarageResponse.getData();

        updateJobEntity(jobEntityOptional.get(), request.getEndingGarageId());

        return true;
    }

    private void updateJobEntity(JobEntity jobEntity, String endingGarageId) {
        jobEntity.setStatus(JobStatus.ASSIGNED.statusCode);
        jobEntity.setAssignedAt(DateTimeHelper.getCurrentTimeInEpoch());
        jobEntity.setEndingGarageId(endingGarageId);

        jobRepository.save(jobEntity);
    }
}
