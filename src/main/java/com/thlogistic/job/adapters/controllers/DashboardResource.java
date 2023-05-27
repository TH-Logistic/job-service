package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dashboard")
interface DashboardResource {

    @GetMapping()
    ResponseEntity<Object> dashboard(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid GetDashboardRequest request);
}
