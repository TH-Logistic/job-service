package com.thlogistic.job.client.transportation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDriverInfoDto {
    String id;
    String avatarUrl;
    String name;
    String gender;
    String phoneNumber;
    String dateOfBirth;
}
