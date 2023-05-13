package com.thlogistic.job.client.transportation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransportationDto {
    String id;
    String licensePlate;
    Double load;
    Integer deliveryStatus;
    Boolean isInGarage;
    GetGarageDto garage;
    GetDriverInfoDto mainDriver;
    GetDriverInfoDto coDriver;
}
