package com.thlogistic.job.client.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDto {
    String id;
    String name;
    String unit;
    List<Integer> types;
    Double basePrice;
}
