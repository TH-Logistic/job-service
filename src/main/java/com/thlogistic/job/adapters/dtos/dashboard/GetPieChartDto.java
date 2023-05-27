package com.thlogistic.job.adapters.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPieChartDto {
    Double tonBasedJobPrice;
    Double tripBasedJobPrice;
}
