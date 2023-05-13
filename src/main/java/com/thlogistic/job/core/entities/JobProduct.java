package com.thlogistic.job.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Entity for mapping GetProductDto from Product Client and JobProductEntity
public class JobProduct {
    String productId;
    Double weight;
    String unit;
    List<Integer> types;
    Double basePrice;
    Double grandTotal;
}
