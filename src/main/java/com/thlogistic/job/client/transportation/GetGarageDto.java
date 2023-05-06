package com.thlogistic.job.client.transportation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGarageDto {
    String id;
    String name;
    String address;
    Double latitude;
    Double longitude;
}
