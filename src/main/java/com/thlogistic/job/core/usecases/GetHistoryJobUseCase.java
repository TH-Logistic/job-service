package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetHistoryJobRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;

import java.util.List;

public interface GetHistoryJobUseCase extends BaseUseCase<BaseTokenRequest<GetHistoryJobRequest>, List<GetJobResponse>> {
}
