package com.thlogistic.job.adapters.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRecentJobDashboardDto {
    String id;
    List<String> products;
    Integer status;
    Long createdAt;
}
