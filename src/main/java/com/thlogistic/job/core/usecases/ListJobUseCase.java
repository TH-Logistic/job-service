package com.thlogistic.job.core.usecases;

import com.thlogistic.job.adapters.dtos.BasePagingResponse;
import com.thlogistic.job.adapters.dtos.GetJobPagingResponse;
import com.thlogistic.job.adapters.dtos.ListJobPagingRequest;
import org.springframework.stereotype.Service;

@Service
public interface ListJobUseCase extends BaseUseCase<ListJobPagingRequest, BasePagingResponse<GetJobPagingResponse>> {
}
