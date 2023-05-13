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
    boolean isHealthcheckOk;
    boolean isTiresOk;
    boolean isLightOk;
    boolean isBrakeOk;
    boolean isFluidLevelOk;
    boolean isBatteryOk;
    boolean isWiperOk;
}
