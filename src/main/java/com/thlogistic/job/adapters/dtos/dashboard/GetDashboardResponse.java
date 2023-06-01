package com.thlogistic.job.adapters.dtos.dashboard;

import com.thlogistic.job.client.billing.GetBillingStatisticResponse;
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
    Integer totalOrders;
    Integer totalTrucks;
    Integer totalProducts;
    Integer totalRoutes;
    GetBillingStatisticResponse billing;
    GetOrderPricePieChartDto orderPricePieChart;
    List<GetProductTypePieChartItemDto> productsPieChart;
    List<GetLineChartItemDto> lineChart;
    List<GetRecentJobDashboardDto> recentJobs;
}
