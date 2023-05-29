package com.thlogistic.job.client.transportation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransportationDeliveryStatusRequest {
    Integer deliveryStatus;
    String garageId;
}
