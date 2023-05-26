package com.thlogistic.job.client.billing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingStatisticOverviewDto {
    Double amount;
    Integer totalOrders;
}
