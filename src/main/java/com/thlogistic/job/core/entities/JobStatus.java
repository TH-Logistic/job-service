package com.thlogistic.job.core.entities;

import com.thlogistic.job.aop.exception.InvalidJobStatusException;

public enum JobStatus {
    OPEN(1),
    ASSIGNED(2),
    JOB_STARTED(3),
    PICK_UP_ARRIVE(4),
    PICK_UP_DONE(5),
    DELIVERY_ARRIVE(6),
    DISCHARGED(7),
    COMPLETED(8);

    public final Integer statusCode;

    JobStatus(Integer code) {
        statusCode = code;
    }

    public static JobStatus fromInt(Integer value) {
        for (JobStatus type: values()) {
            if (type.statusCode.equals(value)) {
                return type;
            }
        }
        throw new InvalidJobStatusException("Job status not found");
    }

}
