package com.thlogistic.job.client.product;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ProductClient {
    @RequestLine("GET /api/v1/product/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetProductDto> getProduct(@Param("token") String token, @Param("id") String id);
}
