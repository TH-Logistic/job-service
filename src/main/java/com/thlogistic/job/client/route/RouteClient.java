package com.thlogistic.job.client.route;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface RouteClient {
    @RequestLine("GET /api/v1/route/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetRouteDto> getRoute(@Param("token") String token, @Param("id") String id);
}
