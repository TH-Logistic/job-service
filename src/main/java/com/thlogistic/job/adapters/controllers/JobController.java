package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.*;
import com.thlogistic.job.adapters.dtos.dashboard.GetDashboardResponse;
import com.thlogistic.job.adapters.dtos.statistic.GetJobStatisticResponse;
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
    private final GetDashboardUseCase getDashboardUseCase;
    private final GetJobUseCase getJobUseCase;
    private final GetNumberOfTripsOfDriverUseCase getNumberOfTripsOfDriverUseCase;
    private final GetCurrentDeliveryJobOfDriverUseCase getCurrentDeliveryJobOfDriverUseCase;
    private final GetJobIfExistUseCase getJobIfExistUseCase;
    private final GetJobPagingUseCase getJobPagingUseCase;
    private final AddTransportationUseCase addTransportationUseCase;
    private final AddEndingGarageUseCase addEndingGarageUseCase;
    private final UpdateJobStatusUseCase updateJobStatusUseCase;
    private final GetUpcomingJobUseCase getUpcomingJobUseCase;
    private final GetHistoryJobUseCase getHistoryJobUseCase;
    private final GetStatisticByProductUseCase getStatisticByProductUseCase;
    private final GetStatisticByRouteUseCase getStatisticByRouteUseCase;
    private final GetStatisticByDriverUseCase getStatisticByDriverUseCase;

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
        BasePagingResponse<GetJobPagingResponse> result = getJobPagingUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        request
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> checkIfJobExist(String token, String id) {
        GetJobIfExistResponse result = getJobIfExistUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        id
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getNumberOfTripsOfDriver(String token, String driverId) {
        Integer result = getNumberOfTripsOfDriverUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        driverId
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getCurrentDeliveryJobOfDriver(String token, String driverId) {
        String result = getCurrentDeliveryJobOfDriverUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        driverId
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> dashboard(String token, GetDashboardRequest request) {
        GetDashboardResponse result = getDashboardUseCase.execute(
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
        GetJobStatisticResponse<GetJobListResponse> result = getStatisticByProductUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        productId
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getStatisticByRoute(String token, String routeId) {
        GetJobStatisticResponse<GetJobListResponse> result = getStatisticByRouteUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        routeId
                )
        );
        return successResponse(result, null);
    }

    @Override
    public ResponseEntity<Object> getStatisticByDriver(String token, String driverId) {
        GetJobStatisticResponse<GetJobListResponse> result = getStatisticByDriverUseCase.execute(
                new BaseTokenRequest<>(
                        token,
                        driverId
                )
        );
        return successResponse(result, null);
    }
}
