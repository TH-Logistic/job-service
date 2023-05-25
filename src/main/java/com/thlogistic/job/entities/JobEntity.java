package com.thlogistic.job.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    String jobId;
    String routeId;
    Integer status;
    Double totalPrice;
    Boolean isTonBased;
    Long mustDeliverAt;
    Long createdAt;
    Long assignedAt;
    Long startedAt;
    Long pickUpArriveAt;
    Long pickUpDoneAt;
    Long deliveryArriveAt;
    Long dischargedAt;
    Long completedAt;
    String pickUpContactName;
    String pickUpContactNo;
    String unloadContactName;
    String unloadContactNo;
    String startingGarageId;
    String endingGarageId;
    String notesToDriver;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<JobProductEntity> jobProductList;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<DriverJobEntity> driverJobList;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<JobTrackingEntity> jobTrackingList;
}
