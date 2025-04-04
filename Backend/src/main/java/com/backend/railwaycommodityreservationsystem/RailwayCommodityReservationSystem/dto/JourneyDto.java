package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.PaymentMethod;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JourneyDto {

    private Long id;
//    private PointDto pickupLocation;
//    private PointDto dropOffLocation;

    private LocalDateTime createdTime;
    private DriverDto driver;
    private TrainDto train;
    private PaymentMethod paymentMethod;

    private JourneyStatus journeyStatus;

//    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
