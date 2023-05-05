package com.thlogistic.job.core.ports;


import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
import com.thlogistic.job.entities.JobProductEntity;
import com.thlogistic.job.entities.JobProductKey;

import java.util.List;
import java.util.Optional;

public interface JobProductRepository {
    JobProductKey insert(JobProductEntity job);
    JobProductKey update(JobProductEntity job);
    Optional<JobProductEntity> findById(String id);
    BasePagingQueryResult<List<JobProductEntity>> paging(String keyword, Integer page, Integer size);
    List<JobProductEntity> findByKeyword(String keyword);

}
