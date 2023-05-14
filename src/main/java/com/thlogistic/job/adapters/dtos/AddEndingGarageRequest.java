package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEndingGarageRequest {
    @NotEmpty(message = "Invalid job ID")
    String jobId;
    @NotEmpty(message = "Invalid ending garage ID")
    String endingGarageId;
}
