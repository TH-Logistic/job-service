package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListJobPagingRequest extends BasePagingRequest {

    @Min(value = 0, message = "Invalid min price")
    Double minOrderFee;

    @Min(value = 0, message = "Invalid max price")
    Double maxOrderFee;

    List<Integer> statusList;

    String keyword;
}
