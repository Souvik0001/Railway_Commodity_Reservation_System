package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.PaymentMethod;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportRequestDto {

    private Long id;

//    private PointDto pickupLocation;
//    private PointDto dropOffLocation;
    private PaymentMethod paymentMethod;

    private LocalDateTime requestedTime;

    private DriverDto rider;
    private Double fare;

    private JourneyRequestStatus journeyRequestStatus;
}
