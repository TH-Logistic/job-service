package com.thlogistic.job.adapters.dtos;

import com.thlogistic.job.client.healthcheck.CreateHealthcheckClientRequest;
import com.thlogistic.job.client.healthcheck.CreateHealthcheckClientResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobStatusRequest {
    @NotNull(message = "Invalid job status")
    Integer jobStatus;
    CreateHealthcheckClientRequest createHealthCheck;
}
