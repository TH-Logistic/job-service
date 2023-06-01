package com.thlogistic.job.core.usecases;

import com.thlogistic.job.core.ports.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetJobTotalUseCaseImpl implements GetJobTotalUseCase {

    private final JobRepository jobRepository;

    @Override
    public Integer execute() {
        return jobRepository.findAll().size();
    }
}
