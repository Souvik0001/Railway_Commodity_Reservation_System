package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {

    private Long id;
    private Double rating;
    private Boolean available;
    private String cycleId;
    private PointDto currentLocation;
}
