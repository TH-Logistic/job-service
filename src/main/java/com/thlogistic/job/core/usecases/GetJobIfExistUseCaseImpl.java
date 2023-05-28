package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.aop.exception.DataNotFoundException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetJobIfExistUseCaseImpl implements GetJobIfExistUseCase {

    private final JobRepository jobRepository;

    @Override
    public GetJobIfExistResponse execute(BaseTokenRequest<String> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String jobId = baseTokenRequest.getRequestContent();
        Optional<JobEntity> entity = jobRepository.findById(jobId);

        if (entity.isEmpty()) {
            throw new DataNotFoundException("Job");
        }

        return new GetJobIfExistResponse(
                entity.get().getJobId(),
                entity.get().getStatus()
        );
    }
}
