package com.thlogistic.job.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobProductKey implements Serializable {
    @Column(name = "job_id")
    private String jobId;
    @Column(name = "product_id")
    private String productId;
}
