package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PagingJobRequest extends BasePagingRequest {
    String keyword;

    @DecimalMin(value = "0.0", message = "Invalid min length")
    Double minOrderFee;

    @DecimalMin(value = "0.0", message = "Invalid max length")
    Double maxOrderFee;
}
