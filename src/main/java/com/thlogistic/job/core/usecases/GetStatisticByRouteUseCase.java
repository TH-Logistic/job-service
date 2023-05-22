package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobListNoRouteResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;

public interface GetStatisticByRouteUseCase extends BaseUseCase<BaseTokenRequest<String>, GetJobStatisticResponse<GetJobListNoRouteResponse>> {
}
