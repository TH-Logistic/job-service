package com.thlogistic.job.client.product;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.client.product.statistic.GetProductStatisticResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ProductClient {

    @RequestLine("GET /api/v1/product/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetProductDto> getProduct(@Param("token") String token, @Param("id") String id);

    @RequestLine("GET /api/v1/product/statistic")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetProductStatisticResponse> getTotalProductsStatistic(@Param("token") String token);

    @RequestLine("GET /api/v1/product/find-all?ids={ids}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<List<GetProductDto>> findAllProductsByIds(@Param("token") String token, @Param("ids") String ids);
}
