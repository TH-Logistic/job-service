package com.thlogistic.job.core.usecases;


import com.thlogistic.job.adapters.dtos.AddEndingGarageRequest;
import com.thlogistic.job.adapters.dtos.AddTransportationRequest;
import com.thlogistic.job.adapters.dtos.BaseTokenRequest;

public interface AddEndingGarageUseCase extends BaseUseCase<BaseTokenRequest<AddEndingGarageRequest>, Boolean> {
}
