package com.thlogistic.job.client.healthcheck;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateHealthcheckClientRequest {
    String jobId;
    Boolean isTiresOk;
    Boolean isLightOk;
    Boolean isBrakeOk;
    Boolean isFluidLevelOk;
    Boolean isBatteryOk;
    Boolean isWiperOk;
    String note;
}
