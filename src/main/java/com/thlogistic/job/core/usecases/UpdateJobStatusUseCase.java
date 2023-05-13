package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.CreateJobResponse;
import com.thlogistic.job.adapters.dtos.UpdateJobStatusRequest;
import kotlin.Pair;

public interface UpdateJobStatusUseCase extends BaseUseCase<BaseTokenRequest<Pair<String, UpdateJobStatusRequest>>, Boolean> {
}
