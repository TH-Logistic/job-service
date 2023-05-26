package com.thlogistic.job.client.billing;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface BillingClient {
    @RequestLine("GET /api/v1/billing/statistic/dashboard")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetBillingStatisticResponse> getStatisticForDashboard(@Param("token") String token);
}
