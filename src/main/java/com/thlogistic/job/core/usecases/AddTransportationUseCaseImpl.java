package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.AddTransportationRequest;
import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.aop.exception.BadRequestException;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddTransportationUseCaseImpl implements AddTransportationUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;
    private final TransportationClient transportationClient;

    @Override
    public Boolean execute(BaseTokenRequest<AddTransportationRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        AddTransportationRequest request = baseTokenRequest.getRequestContent();

        BaseResponse<GetTransportationDto> getTransportationResponse;

        // Check valid transportation Id
        try {
            getTransportationResponse = transportationClient.getTransportation(token, request.getTransportationId());
        } catch (Exception e) {
            throw new DataNotFoundException("Transportation");
        }

        // Check valid job Id
        Optional<JobEntity> jobEntityOptional = jobRepository.findById(request.getJobId());
        if (jobEntityOptional.isEmpty()) {
            throw new DataNotFoundException("Job");
        }

        GetTransportationDto getTransportationDto = getTransportationResponse.getData();

        // Check whether transportation is in garage
        if (!getTransportationDto.getIsInGarage() || getTransportationDto.getGarage() == null) {
            throw new BadRequestException("The transportation is not in garage");
        }

        addDriverJob(
                jobEntityOptional.get(),
                getTransportationDto.getMainDriver().getId(),
                getTransportationDto.getCoDriver().getId()
        );

        updateJobEntity(jobEntityOptional.get(), getTransportationDto.getGarage().getId());

        return true;
    }

    private void updateJobEntity(JobEntity jobEntity, String startingGarageId) {
        jobEntity.setStatus(JobStatus.ASSIGNED.statusCode);
        jobEntity.setAssignedAt(DateTimeHelper.getCurrentTimeInEpoch());
        jobEntity.setStartingGarageId(startingGarageId);

        jobRepository.save(jobEntity);
    }

    private void addDriverJob(JobEntity jobEntity, String mainDriverId, String coDriverId) {
        DriverJobEntity mainDriver = DriverJobEntity.builder()
                .driverId(mainDriverId)
                .job(jobEntity)
                .build();

        DriverJobEntity coDriver = DriverJobEntity.builder()
                .driverId(coDriverId)
                .job(jobEntity)
                .build();

        driverJobRepository.save(mainDriver);
        driverJobRepository.save(coDriver);
    }
}
