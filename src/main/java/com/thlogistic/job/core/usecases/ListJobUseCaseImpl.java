package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListJobUseCaseImpl implements ListJobUseCase {

    private final DriverJobRepository driverJobRepository;
    private final GetJobUseCase getJobUseCase;

    @Override
    public BasePagingResponse<GetJobPagingResponse> execute(ListJobPagingRequest listJobPagingRequest) {
        return null;
    }
}
