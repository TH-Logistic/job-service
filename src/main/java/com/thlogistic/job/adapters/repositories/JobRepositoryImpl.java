package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.core.ports.JobRepository;
import com.thlogistic.job.entities.JobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository {
    private final PostgresJobRepository repository;

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
    public BasePagingQueryResult<List<JobEntity>> paging(String keyword, Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<JobEntity> jobs;
//        if (keyword == null || keyword.isEmpty()) {
//            jobs = repository.findAll(pageable);
//        } else {
//            jobs = repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable);
//        }
//
//        BasePagingQueryResult<List<JobEntity>> result = new BasePagingQueryResult<>();
//        result.data = jobs.getContent();
//        result.total = jobs.getTotalElements();
//        result.totalPage = jobs.getTotalPages();
//        return result;
        return null;
    }

    @Override
    public List<JobEntity> findByKeyword(String keyword) {
//        if (keyword == null || keyword.isEmpty()) {
//            throw new RuntimeException("Invalid keyword");
//        }
//        return repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
        return null;
    }
}
