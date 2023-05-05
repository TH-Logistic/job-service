package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresJobRepository extends JpaRepository<JobEntity, String> {

}
