package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.*;
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
    ResponseEntity<Object> listJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid ListJobPagingRequest request);

    @GetMapping("/{id}/exists")
    ResponseEntity<Object> checkIfJobExist(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    @GetMapping("/number-of-trips/{driverId}")
    ResponseEntity<Object> getNumberOfTripsOfDriver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);

    @GetMapping("/current-delivery/{driverId}")
    ResponseEntity<Object> getCurrentDeliveryJobOfDriver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);

    @GetMapping("/dashboard")
    ResponseEntity<Object> dashboard(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid GetDashboardRequest request);

    @PostMapping
    ResponseEntity<Object> createJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateJobRequest request);

    @PutMapping("/job-status/{jobId}")
    ResponseEntity<Object> updateJobStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody UpdateJobStatusRequest request, @PathVariable String jobId);

    @PostMapping("/add-transportation")
    ResponseEntity<Object> addTransportation(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AddTransportationRequest request);

    @PostMapping("/add-ending-garage")
    ResponseEntity<Object> addEndingGarage(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AddEndingGarageRequest request);

    @GetMapping("/upcoming-jobs/{driverId}")
    ResponseEntity<Object> getUpcomingJobs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);

    @GetMapping("/history-jobs/{driverId}")
    ResponseEntity<Object> getHistoryJobs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId, @RequestParam String date);

    @GetMapping("/statistic/product/{productId}")
    ResponseEntity<Object> getStatisticByProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String productId);

    @GetMapping("/statistic/route/{routeId}")
    ResponseEntity<Object> getStatisticByRoute(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String routeId);

    @GetMapping("/statistic/driver/{driverId}")
    ResponseEntity<Object> getStatisticByDriver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String driverId);
}
