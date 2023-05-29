package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;

public interface GetNumberOfTripsOfDriverUseCase extends BaseUseCase<BaseTokenRequest<String>, Integer> {
}
