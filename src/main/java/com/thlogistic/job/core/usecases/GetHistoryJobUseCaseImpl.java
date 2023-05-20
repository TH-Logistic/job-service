package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetHistoryJobRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHistoryJobUseCaseImpl implements GetHistoryJobUseCase {

    private final DriverJobRepository driverJobRepository;
    private final GetJobUseCase getJobUseCase;

    @Override
    public List<GetJobResponse> execute(BaseTokenRequest<GetHistoryJobRequest> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String driverId = baseTokenRequest.getRequestContent().getDriverId();
        String date = baseTokenRequest.getRequestContent().getDate();

        List<DriverJobEntity> driverJobEntities = driverJobRepository.findHistoryJobByDriverIdAndJobStatus(driverId, date, JobStatus.COMPLETED.statusCode);
        List<GetJobResponse> responses = new LinkedList<>();
        driverJobEntities.forEach(e -> {
            GetJobResponse response = getJobUseCase.execute(new BaseTokenRequest<>(token, e.getJob().getJobId()));
            responses.add(response);
        });
        return responses;
    }
}
