package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.AddTransportationRequest;
import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.PagingJobRequest;
import com.thlogistic.job.adapters.dtos.UpdateJobStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/job")
interface JobResource {

    @GetMapping("/{id}")
    ResponseEntity<Object> getJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    @GetMapping("/list")
    ResponseEntity<Object> listJob(@Valid PagingJobRequest request);

    @PostMapping
    ResponseEntity<Object> createJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateJobRequest request);

    @PutMapping("/job-status/{jobId}")
    ResponseEntity<Object> updateJobStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody UpdateJobStatusRequest request, @PathVariable String jobId);

    @PostMapping("/add-transportation")
    ResponseEntity<Object> addTransportation(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AddTransportationRequest request);

    @GetMapping("/upcoming-jobs/{driverId}")
    ResponseEntity<Object> getUpcomingJobs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);

    @GetMapping("/history-jobs/{driverId}")
    ResponseEntity<Object> getHistoryJobs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);
}
