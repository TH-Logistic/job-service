package com.thlogistic.job.client.product.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductStatisticResponse {
    Integer total;
    List<GetProductTypeWithCountItemDto> products;
}
