package com.thlogistic.job.adapters.repositories;

import com.thlogistic.job.core.ports.DriverJobRepository;
import com.thlogistic.job.entities.DriverJobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverJobRepositoryImpl implements DriverJobRepository {
    private final PostgresDriverJobRepository repository;

    @Override
    public String insert(DriverJobEntity driverJob) {
        return repository.save(driverJob).getDriverJobId();
    }

    @Override
    public String save(DriverJobEntity driverJob) {
        return repository.save(driverJob).getDriverJobId();
    }

    @Override
    public Optional<DriverJobEntity> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<DriverJobEntity> findByJobId(String jobId) {
        return repository.findByJob_JobId(jobId);
    }

    @Override
    public List<DriverJobEntity> findUpcomingJobByDriverIdAndJobStatus(String driverId, Integer status) {
        return repository.findByDriverIdIsAndJob_StatusAndJob_EndingGarageIdNotNull(driverId, status);
    }

    @Override
    public List<DriverJobEntity> findHistoryJobByDriverIdAndJobStatus(String driverId, String date, Integer status) {
        return repository.findByDriverIdAndJob_CreatedAtContainsAndJob_Status(driverId, date, status);
    }

    @Override
    public BasePagingQueryResult<List<DriverJobEntity>> paging(String keyword, Integer page, Integer size) {
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
    public List<DriverJobEntity> findByKeyword(String keyword) {
//        if (keyword == null || keyword.isEmpty()) {
//            throw new RuntimeException("Invalid keyword");
//        }
//        return repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
        return null;
    }
}
