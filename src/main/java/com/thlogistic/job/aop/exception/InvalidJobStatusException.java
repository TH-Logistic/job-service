package com.thlogistic.job.aop.exception;

public class InvalidJobStatusException extends RuntimeException {
    public InvalidJobStatusException(String message) {
        super(message);
    }
}
