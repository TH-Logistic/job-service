package com.thlogistic.job.adapters.dtos;

import com.thlogistic.job.client.transportation.GetDriverInfoDto;
import com.thlogistic.job.client.transportation.GetGarageDto;
import com.thlogistic.job.client.transportation.GetTransportationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetJobResponse {
    GetTransportationDto transportation;
    GetGarageDto startingGarage;
    GetGarageDto endingGarage;
    GetDriverInfoDto mainDriver;
    GetDriverInfoDto coDriver;
    // TODO: Healthcheck
    // TODO: Route
    Integer status;
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
    String startingGarageId;
    String endingGarageId;
    String notesToDriver;
}
