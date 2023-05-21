package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.core.ports.JobProductRepository;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobProductRepositoryImpl implements JobProductRepository {
    private final PostgresJobProductRepository repository;

    @Override
    public JobProductKey insert(JobProductEntity jobProduct) {
        return repository.save(jobProduct).getJobProductKey();
    }

    @Override
    public JobProductKey update(JobProductEntity jobProduct) {
        return null;
    }

    @Override
    public List<JobProductEntity> findAllByJobId(String jobId) {
        return repository.findAllByJob_JobId(jobId);
    }

    @Override
    public List<JobProductEntity> findByProductId(String productId) {
        return repository.findAllByJobProductKey_ProductId(productId);
    }
}
