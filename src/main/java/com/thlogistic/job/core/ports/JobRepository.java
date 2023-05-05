package com.thlogistic.job.core.ports;


import com.thlogistic.job.adapters.repositories.BasePagingQueryResult;
import com.thlogistic.job.entities.JobEntity;

import java.util.List;
import java.util.Optional;

public interface JobRepository {
    String insert(JobEntity job);
    String save(JobEntity job);
    Optional<JobEntity> findById(String id);
    BasePagingQueryResult<List<JobEntity>> paging(String keyword, Integer page, Integer size);
    List<JobEntity> findByKeyword(String keyword);

}
