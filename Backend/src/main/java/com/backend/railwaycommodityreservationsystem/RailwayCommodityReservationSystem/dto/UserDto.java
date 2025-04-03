package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String email;
    private Set<Role> roles;
}
