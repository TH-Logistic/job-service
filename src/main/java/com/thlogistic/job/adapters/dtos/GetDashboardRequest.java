package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDashboardRequest {
    @NotNull(message = "Invalid year")
    @Min(value = 0, message = "Invalid year")
    Integer year;
}
