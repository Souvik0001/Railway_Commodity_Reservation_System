package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Long id;
    private UserDto user;
    private Double rating;
}
