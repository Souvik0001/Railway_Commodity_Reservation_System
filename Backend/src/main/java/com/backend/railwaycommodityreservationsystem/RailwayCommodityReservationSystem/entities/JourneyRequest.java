package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.PaymentMethod;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class JourneyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(columnDefinition = "Geometry(Point, 4326)")
//    private Point pickupLocation;
//
//    @Column(columnDefinition = "Geometry(Point, 4326)")
//    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private JourneyRequestStatus journeyRequestStatus;

    private Double fare;
}
