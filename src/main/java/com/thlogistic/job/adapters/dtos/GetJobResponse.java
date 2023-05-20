package com.thlogistic.job.adapters.dtos;

import com.thlogistic.job.client.healthcheck.GetHealthcheckClientResponse;
import com.thlogistic.job.client.product.GetProductDto;
import com.thlogistic.job.client.route.GetRouteDto;
import com.thlogistic.job.client.transportation.GetDriverInfoDto;
import com.thlogistic.job.client.transportation.GetGarageDto;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetJobResponse {
    String id;
    GetTransportationDto transportation;
    GetGarageDto startingGarage;
    GetGarageDto endingGarage;
    List<GetProductDto> products;
    GetRouteDto route;
    GetHealthcheckClientResponse healthcheck;
    Integer status;
    Double totalPrice;
    Boolean isTonBased;
    String mustDeliverAt;
    String createdAt;
    String assignedAt;
    String acceptedAt;
    String pickUpArriveAt;
    String pickUpDoneAt;
    String unloadArriveAt;
    String unloadDoneAt;
    String completedAt;
    String pickUpContactName;
    String pickUpContactNo;
    String unloadContactName;
    String unloadContactNo;
    String notesToDriver;
}
