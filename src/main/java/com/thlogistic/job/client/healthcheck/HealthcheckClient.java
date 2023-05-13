package com.thlogistic.job.client.healthcheck;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface HealthcheckClient {
    @RequestLine("GET /api/v1/healthcheck/{jobId}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetHealthcheckClientResponse> getHealthcheck(@Param("token") String token, @Param("jobId") String jobId);

    @RequestLine("POST /api/v1/healthcheck")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<CreateHealthcheckClientResponse> createHealthcheck(@Param("token") String token, @RequestBody CreateHealthcheckClientRequest healthcheckRequest);
}
