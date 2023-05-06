package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTransportationRequest {
    @NotEmpty(message = "Invalid job ID")
    String jobId;
    @NotEmpty(message = "Invalid transportation ID")
    String transportationId;
}
