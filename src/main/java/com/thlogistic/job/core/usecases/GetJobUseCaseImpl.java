package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.aop.exception.InvalidDateTimeFormatException;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import com.thlogistic.job.utils.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetJobUseCaseImpl implements GetJobUseCase {

    private final JobRepository jobRepository;
    private final DriverJobRepository driverJobRepository;

    @Override
    public GetJobResponse execute(String id) {
        return null;
    }
}
