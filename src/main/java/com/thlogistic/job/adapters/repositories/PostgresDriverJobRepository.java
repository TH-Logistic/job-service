package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostgresDriverJobRepository extends JpaRepository<DriverJobEntity, String> {
    List<DriverJobEntity> findByJob_JobId(String jobId);
    List<DriverJobEntity> findByDriverId(String driverId);
    List<DriverJobEntity> findByDriverIdIsAndJob_StatusAndJob_EndingGarageIdNotNull(String driverId, Integer status);
    List<DriverJobEntity> findByDriverIdAndJob_CreatedAtContainsAndJob_Status(String driverId, String date,Integer status);
}
