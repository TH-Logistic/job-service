package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;

import java.util.List;

public interface GetUpcomingJobUseCase extends BaseUseCase<BaseTokenRequest<String>, List<GetJobResponse>> {
}
