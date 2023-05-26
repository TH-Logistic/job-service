package com.thlogistic.job.client.billing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBillingDto {
    String id;
    String title;
    String description;
    Double cost;
    String createdAt;
    Integer status;
    Integer type;
    Boolean isRequestedByDriver;
}
