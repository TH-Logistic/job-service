package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobListResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;
import com.thlogistic.job.adapters.dtos.statistic.JobStatisticDto;
import com.thlogistic.job.aop.exception.CustomRuntimeException;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetNumberOfTripsOfDriverUseCaseImpl implements GetNumberOfTripsOfDriverUseCase {
    private final DriverJobRepository driverJobRepository;

    @Override
    public Integer execute(BaseTokenRequest<String> baseTokenRequest) {
        String token = baseTokenRequest.getToken();
        String driverId = baseTokenRequest.getRequestContent();
        return driverJobRepository.findByDriverId(driverId).size();
    }
}
