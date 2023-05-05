package com.thlogistic.job.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "job_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobProductEntity {
    @Id
    JobProductKey jobProductKey;
    Double weight;
    Double grandTotal;

    @ManyToOne
    @MapsId("jobId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    JobEntity job;
}
