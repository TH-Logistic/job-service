package com.thlogistic.job.client.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLocationDto {
    String id;
    String name;
    String address;
    Double latitude;
    Double longitude;
}
