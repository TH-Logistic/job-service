package com.thlogistic.job.client.healthcheck;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.client.product.GetProductDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface HealthcheckClient {
    @RequestLine("GET /api/v1/healthcheck/{jobId}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetHealthcheckDto> getHealthcheck(@Param("token") String token, @Param("jobId") String jobId);
}
