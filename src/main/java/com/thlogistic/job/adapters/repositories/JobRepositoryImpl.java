package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import com.thlogistic.job.utils.DateTimeHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository {
    private final PostgresJobRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String insert(JobEntity job) {
        return repository.save(job).getJobId();
    }

    @Override
    public String save(JobEntity job) {
        return repository.save(job).getJobId();
    }

    @Override
    public Optional<JobEntity> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public BasePagingQueryResult<List<JobEntity>> paging(String keyword,
                                                         List<Integer> statusList,
                                                         Double minPrice,
                                                         Double maxPrice,
                                                         Integer page,
                                                         Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobEntity> jobs;

        // TODO: Fix this shitty code please, too much if else conditions here
        if (statusList == null || statusList.isEmpty()) {
            if (keyword == null || keyword.isEmpty()) {
                jobs = repository.findAllByTotalPriceBetween(minPrice, maxPrice, pageable);
            } else {
                jobs = repository.findAllByJobIdContainingAndTotalPriceBetween(keyword, minPrice, maxPrice, pageable);
            }
        } else {
            if (keyword == null || keyword.isEmpty()) {
                jobs = repository.findAllByStatusIsInAndTotalPriceBetween(statusList, minPrice, maxPrice, pageable);
            } else {
                jobs = repository.findAllByJobIdContainingAndStatusIsInAndTotalPriceBetween(keyword, statusList, minPrice, maxPrice, pageable);
            }
        }

        BasePagingQueryResult<List<JobEntity>> result = new BasePagingQueryResult<>();
        result.data = jobs.getContent();
        result.total = jobs.getTotalElements();
        result.totalPage = jobs.getTotalPages();
        return result;
    }

    @Override
    public List<JobEntity> findByRouteId(String routeId) {
        return repository.findAllByRouteId(routeId);
    }

    @Override
    public List<JobEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<JobEntity> findLastedJob() {
        return repository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public List<JobEntity> findAllJobInYear(Integer year) {
        Long startTimestamp = DateTimeHelper.getStartOfYearTimestamp(year);
        Long endTimestamp = DateTimeHelper.getEndOfYearTimestamp(year);

        String jpql = "SELECT j FROM job j WHERE j.createdAt >= :startTimestamp AND j.createdAt <= :endTimestamp";

        TypedQuery<JobEntity> query = entityManager.createQuery(jpql, JobEntity.class);
        query.setParameter("startTimestamp", startTimestamp);
        query.setParameter("endTimestamp", endTimestamp);
        return query.getResultList();
    }
}
