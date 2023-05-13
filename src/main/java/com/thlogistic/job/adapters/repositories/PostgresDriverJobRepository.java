package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.entities.DriverJobEntity;
import com.thlogistic.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostgresDriverJobRepository extends JpaRepository<DriverJobEntity, String> {
    Optional<DriverJobEntity> findByJob_JobId(String jobId);
}
