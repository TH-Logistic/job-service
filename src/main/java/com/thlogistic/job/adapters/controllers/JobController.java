package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.core.usecases.*;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController extends BaseController implements JobResource {

    private final CreateJobUseCase createJobUseCase;
    private final GetJobUseCase getJobUseCase;
    private final ListJobUseCase listJobUseCase;
    private final AddTransportationUseCase addTransportationUseCase;
    private final AddEndingGarageUseCase addEndingGarageUseCase;
    private final UpdateJobStatusUseCase updateJobStatusUseCase;
    private final GetUpcomingJobUseCase getUpcomingJobUseCase;
    private final GetHistoryJobUseCase getHistoryJobUseCase;

    @Override
    public ResponseEntity<Object> getJob(String token, String id) {
        GetJobResponse result = getJobUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        id
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> listJob(String token, ListJobPagingRequest request) {
        BasePagingResponse<GetJobPagingResponse> result = listJobUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        request
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> createJob(String token, CreateJobRequest request) {
        CreateJobResponse result = createJobUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        request
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> updateJobStatus(String token, UpdateJobStatusRequest request, String jobId) {
        updateJobStatusUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        new Pair<>(jobId, request)
                )
        );
        return successResponse(true, null);
    }

    @Override
    public ResponseEntity<Object> addTransportation(String token, AddTransportationRequest request) {
        addTransportationUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        request
                )
        );
        return successResponse(true, null);
    }

    @Override
    public ResponseEntity<Object> addEndingGarage(String token, AddEndingGarageRequest request) {
        addEndingGarageUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        request
                )
        );
        return successResponse(true, null);
    }

    @Override
    public ResponseEntity<Object> getUpcomingJobs(String token, String driverId) {
        List<GetJobResponse> result = getUpcomingJobUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        driverId
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getHistoryJobs(String token, String driverId, String date) {
        List<GetJobResponse> result = getHistoryJobUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        new GetHistoryJobRequest(driverId, date)
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getStatisticByProduct(String token, String productId) {
        return null;
    }
}
