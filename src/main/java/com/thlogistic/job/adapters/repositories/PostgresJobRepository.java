package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.core.entities.JobStatus;
import com.thlogistic.job.entities.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostgresJobRepository extends JpaRepository<JobEntity, String> {

    Page<JobEntity> findAllByTotalPriceBetween(
            Double minPrice, Double maxPrice, Pageable pageable
    );

    Page<JobEntity> findAllByJobIdContainingAndTotalPriceBetween(
            String jobId, Double minPrice, Double maxPrice, Pageable pageable
    );

    Page<JobEntity> findAllByStatusIsInAndTotalPriceBetween(
            Collection<Integer> statusList, Double minPrice, Double maxPrice, Pageable pageable
    );

    Page<JobEntity> findAllByJobIdContainingAndStatusIsInAndTotalPriceBetween(
            String jobId, Collection<Integer> statusList, Double minPrice, Double maxPrice, Pageable pageable
    );

    List<JobEntity> findAllByRouteId(String routeId);

    List<JobEntity> findTop10ByOrderByCreatedAtDesc();
}
