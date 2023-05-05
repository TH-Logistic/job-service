package com.thlogistic.job.client.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSimpleLocationDto {
    String id;
    String name;
    String address;
}
