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
    public BasePagingQueryResult<List<JobProductEntity>> paging(String keyword, Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<JobProductEntity> jobProducts;
//        if (keyword == null || keyword.isEmpty()) {
//            jobProducts = repository.findAll(pageable);
//        } else {
//            jobProducts = repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable);
//        }
//
//        BasePagingQueryResult<List<JobProductEntity>> result = new BasePagingQueryResult<>();
//        result.data = jobProducts.getContent();
//        result.total = jobProducts.getTotalElements();
//        result.totalPage = jobProducts.getTotalPages();
//        return result;
        return null;
    }

    @Override
    public List<JobProductEntity> findByKeyword(String keyword) {
//        if (keyword == null || keyword.isEmpty()) {
//            throw new RuntimeException("Invalid keyword");
//        }
//        return repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
        return null;
    }
}
