package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateJobRequest {
    @NotEmpty(message = "Product ID cannot be empty")
    String productId;

    @Min(value = 0, message = "Invalid weight")
    Double weight;
}
