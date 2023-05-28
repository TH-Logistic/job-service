package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobIfExistResponse;
import com.thlogistic.job.adapters.dtos.GetJobResponse;

public interface GetJobIfExistUseCase extends BaseUseCase<BaseTokenRequest<String>, GetJobIfExistResponse> {
}
