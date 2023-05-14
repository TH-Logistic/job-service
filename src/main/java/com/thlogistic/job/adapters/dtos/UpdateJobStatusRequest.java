package com.thlogistic.job.adapters.dtos;

import com.thlogistic.job.client.healthcheck.CreateHealthcheckClientRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobStatusRequest {
    @NotNull(message = "Invalid job status")
    Integer jobStatus;
    CreateHealthcheckClientRequest healthcheck;
}
