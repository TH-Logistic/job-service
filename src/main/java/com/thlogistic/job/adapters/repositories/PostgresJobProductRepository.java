package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresJobProductRepository extends JpaRepository<JobProductEntity, JobProductKey> {

}
