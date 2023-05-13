package com.thlogistic.job.client.transportation;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.client.route.GetRouteDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TransportationClient {
    @RequestLine("GET /api/v1/transportation/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetTransportationDto> getTransportation(@Param("token") String token, @Param("id") String id);

    @RequestLine("GET /api/v1/transportation/find-by-driver/{driverId}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetTransportationDto> getTransportationByDriverId(@Param("token") String token, @Param("id") String driverId);

    @RequestLine("GET /api/v1/garage/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetGarageDto> getGarage(@Param("token") String token, @Param("id") String id);
}
