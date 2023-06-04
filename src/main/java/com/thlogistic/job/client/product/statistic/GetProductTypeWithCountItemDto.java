package com.thlogistic.job.client.product.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductTypeWithCountItemDto {
    Integer type;
    Integer total;
}
