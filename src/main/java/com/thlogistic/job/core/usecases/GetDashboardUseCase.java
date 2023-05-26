package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.GetDashboardRequest;
import com.thlogistic.job.adapters.dtos.GetHistoryJobRequest;
import com.thlogistic.job.adapters.dtos.GetJobResponse;

import java.util.List;

public interface GetDashboardUseCase extends BaseUseCase<BaseTokenRequest<GetDashboardRequest>, List<GetJobResponse>> {
}
