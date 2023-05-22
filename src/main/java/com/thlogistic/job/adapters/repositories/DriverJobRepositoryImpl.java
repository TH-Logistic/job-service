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
    public List<DriverJobEntity> findByDriverId(String driverId) {
        return repository.findByDriverId(driverId);
    }
}
