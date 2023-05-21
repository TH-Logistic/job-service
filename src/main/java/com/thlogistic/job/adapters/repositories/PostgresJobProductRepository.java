package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostgresJobProductRepository extends JpaRepository<JobProductEntity, JobProductKey> {
    List<JobProductEntity> findAllByJob_JobId(String jobId);

    List<JobProductEntity> findAllByJobProductKey_ProductId(String jobId);
}
