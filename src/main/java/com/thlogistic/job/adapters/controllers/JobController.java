package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.BaseTokenRequest;
import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.CreateJobResponse;
import com.thlogistic.job.adapters.dtos.PagingJobRequest;
import com.thlogistic.job.core.usecases.CreateJobUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController extends BaseController implements JobResource {

    private final CreateJobUseCase createJobUseCase;

    @Override
    public ResponseEntity<Object> listJob(PagingJobRequest request) {
        return null;
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

//    private final CreateLocationUseCase createLocationUseCase;
//    private final UpdateLocationUseCase updateLocationUseCase;
//    private final PagingLocationUseCase pagingLocationUseCase;
//
//    @Override
//    public ResponseEntity<Object> listLocation(PagingLocationRequest request) {
//        BasePagingResponse<GetLocationResponse> result = pagingLocationUseCase.execute(request);
//        return successResponse(result, null);
//    }
//
//    @Override
//    public ResponseEntity<Object> createLocation(CreateLocationRequest request) {
//        CreateLocationResponse result = createLocationUseCase.execute(request);
//        return successResponse(result, null);
//    }
//
//    @Override
//    public ResponseEntity<Object> updateLocation(UpdateLocationRequest request, String id) {
//        updateLocationUseCase.execute(Pair.of(id, request));
//        return successResponse(true, null);
//    }

}
