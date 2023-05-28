package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateHealthcheckRequest {
    @NotNull(message = "Invalid tires' status value")
    Boolean isTiresOk;

    @NotNull(message = "Invalid light's status value")
    Boolean isLightOk;

    @NotNull(message = "Invalid brake's status value")
    Boolean isBrakeOk;

    @NotNull(message = "Invalid fluid level's status value")
    Boolean isFluidLevelOk;

    @NotNull(message = "Invalid battery's status value")
    Boolean isBatteryOk;

    @NotNull(message = "Invalid wiper's status value")
    Boolean isWiperOk;

    String note;
}
