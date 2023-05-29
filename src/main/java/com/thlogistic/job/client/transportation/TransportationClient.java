package com.thlogistic.job.client.transportation;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import com.thlogistic.job.client.route.GetRouteDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransportationClient {
    @RequestLine("GET /api/v1/transportation/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetTransportationDto> getTransportation(@Param("token") String token, @Param("id") String id);

    @RequestLine("GET /api/v1/transportation/find-by-driver/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetTransportationDto> getTransportationByDriverId(@Param("token") String token, @Param("id") String id);

    @RequestLine("GET /api/v1/garage/{id}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<GetGarageDto> getGarage(@Param("token") String token, @Param("id") String id);

    @RequestLine("PUT /api/v1/transportation/update-delivery-status/{transportationId}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<Boolean> updateDeliveryStatus(@Param("token") String token, @Param("transportationId") String transportationId, @RequestBody UpdateTransportationDeliveryStatusRequest body);
}
