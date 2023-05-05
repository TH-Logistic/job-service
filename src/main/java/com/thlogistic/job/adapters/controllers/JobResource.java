package com.thlogistic.job.adapters.controllers;

import com.thlogistic.job.adapters.dtos.CreateJobRequest;
import com.thlogistic.job.adapters.dtos.PagingJobRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/job")
interface JobResource {
    @GetMapping("/list")
    ResponseEntity<Object> listJob(@Valid PagingJobRequest request);

    @PostMapping
    ResponseEntity<Object> createJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateJobRequest request);

//    @PutMapping("/{id}")
//    ResponseEntity<Object> updateJob(@Valid @RequestBody UpdateJobRequest request, @PathVariable String id);
}
