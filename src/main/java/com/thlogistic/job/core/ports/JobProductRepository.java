package com.thlogistic.job.core.ports;


import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;

import java.util.List;
import java.util.Optional;

public interface JobProductRepository {
    JobProductKey insert(JobProductEntity job);
    JobProductKey update(JobProductEntity job);
    List<JobProductEntity> findAllByJobId(String jobId);
    List<JobProductEntity> findByProductId(String productId);

}
