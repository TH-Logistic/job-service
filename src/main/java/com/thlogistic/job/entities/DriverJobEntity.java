package com.thlogistic.job.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "driver_job")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverJobEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    String driverJobId;
    String driverId;
    Double labourSalary;
    Double additionalSalary;
    Integer type;
    Double distance;
    String comment;
    @ManyToOne
    @JoinColumn(name = "job_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    JobEntity job;
}
