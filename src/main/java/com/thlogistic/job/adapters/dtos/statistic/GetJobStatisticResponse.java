package com.thlogistic.job.adapters.dtos.statistic;

import com.thlogistic.job.adapters.dtos.GetJobPagingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetJobStatisticResponse {
    JobStatisticDto statistic;
    List<GetJobPagingResponse> jobs;
}
