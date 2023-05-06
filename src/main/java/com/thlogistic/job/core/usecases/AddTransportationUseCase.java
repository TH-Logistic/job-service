package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.AddTransportationRequest;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.CreateJobResponse;

public interface AddTransportationUseCase extends BaseUseCase<BaseTokenRequest<AddTransportationRequest>, Boolean> {
}
