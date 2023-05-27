package com.thlogistic.job.adapters.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDashboardResponse {
    GetPieChartDto pieChart;
    List<GetLineChartItemDto> lineChart;
    List<GetRecentJobDashboardDto> recentJobs;
}
