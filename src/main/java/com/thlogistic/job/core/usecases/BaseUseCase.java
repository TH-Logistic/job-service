package com.thlogistic.job.core.usecases;

public interface BaseUseCase<Request, Response> {
    Response execute(Request request);
}
