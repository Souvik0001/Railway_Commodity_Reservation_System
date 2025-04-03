package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TransportRequestDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.DriverDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    JourneyDto requestRide(TransportRequestDto transportRequestDto, String cycleId);

    JourneyDto endRide(Long rideId);

    TrainDto rateCycle(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<JourneyDto> getAllMyRides(PageRequest pageRequest);

    Driver createNewRider(User user);

    Driver getCurrentRider();
}
