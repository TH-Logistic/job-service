package com.thlogistic.job.client.billing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBillingStatisticResponse {
    BillingStatisticOverviewDto profit;
    BillingStatisticOverviewDto debt;
    List<GetBillingDto> billings;
}
