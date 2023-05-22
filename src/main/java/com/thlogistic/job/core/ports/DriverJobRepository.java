package com.thlogistic.job.core.ports;


import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
import com.thlogistic.job.entities.DriverJobEntity;

import java.util.List;
import java.util.Optional;

public interface DriverJobRepository {
    String insert(DriverJobEntity job);
    String save(DriverJobEntity job);
    Optional<DriverJobEntity> findById(String id);
    List<DriverJobEntity> findByJobId(String jobId);
    List<DriverJobEntity> findUpcomingJobByDriverIdAndJobStatus(String driverId, Integer status);
    List<DriverJobEntity> findHistoryJobByDriverIdAndJobStatus(String driverId, String date, Integer status);
    List<DriverJobEntity> findByDriverId(String driverId);

}
