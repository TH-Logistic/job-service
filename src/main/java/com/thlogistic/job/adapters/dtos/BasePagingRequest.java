package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BasePagingRequest {
    @NotNull(message = "Invalid page")
    @Min(value = 0, message = "Invalid page")
    Integer page;

    @NotNull(message = "Invalid size")
    @Min(value = 0, message = "Invalid size")
    Integer size;
}
