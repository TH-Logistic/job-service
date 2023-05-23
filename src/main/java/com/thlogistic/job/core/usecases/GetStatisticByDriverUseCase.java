package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobListResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;

public interface GetStatisticByDriverUseCase extends BaseUseCase<BaseTokenRequest<String>, GetJobStatisticResponse<GetJobListResponse>> {
}
