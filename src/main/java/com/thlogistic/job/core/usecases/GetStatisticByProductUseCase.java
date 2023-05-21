package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;

public interface GetStatisticByProductUseCase extends BaseUseCase<BaseTokenRequest<String>, GetJobStatisticResponse> {
}