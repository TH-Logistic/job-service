package com.thlogistic.job.client.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    String id;
    String status;
    String name;
    String username;
    String phoneNumber;
    String email;
    String birthday;
    String avatar;
    String role;
    String deletedAt;
    String bankName;
    String bankAccount;
}
