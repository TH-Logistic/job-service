package com.thlogistic.job.client.healthcheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetHealthcheckClientResponse {
    String id;
    String note;
    String createdAt;
    Boolean isHealthcheckOk;
    Boolean isTiresOk;
    Boolean isLightOk;
    Boolean isBrakeOk;
    Boolean isFluidLevelOk;
    Boolean isBatteryOk;
    Boolean isWiperOk;
}
