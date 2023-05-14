package com.thlogistic.job.adapters.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetJobPagingResponse {
    String licensePlate;
    String driverInCharge;
    List<Integer> productTypeList;
    String createdAt;
    String pickUpAt;
    String unloadAt;
    Double orderFee;
    Integer status;
}
