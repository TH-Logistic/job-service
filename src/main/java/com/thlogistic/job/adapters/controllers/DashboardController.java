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
public class DashboardController extends BaseController implements DashboardResource {

    private final GetDashboardUseCase getDashboardUseCase;

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
}
