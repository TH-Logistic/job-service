package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCurrentDeliveryJobOfDriverUseCaseImpl implements GetCurrentDeliveryJobOfDriverUseCase {
    private final DriverJobRepository driverJobRepository;

    @Override
    public String execute(BaseTokenRequest<String> baseTokenRequest) {
        String driverId = baseTokenRequest.getRequestContent();
        Optional<DriverJobEntity> driverJobEntity = driverJobRepository.findByDriverId(driverId).stream()
                .filter(entity -> isInDeliveryStatus(entity.getJob().getStatus()))
                .findFirst();
        return driverJobEntity.map(entity -> entity.getJob().getJobId()).orElse(null);
    }

    private boolean isInDeliveryStatus(Integer status) {
        return status >= JobStatus.JOB_STARTED.statusCode && status <= JobStatus.DISCHARGED.statusCode;
    }
}
