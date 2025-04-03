package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.SignupDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto signup(SignupDto signupDto);

    String refreshToken(String refreshToken);
}
