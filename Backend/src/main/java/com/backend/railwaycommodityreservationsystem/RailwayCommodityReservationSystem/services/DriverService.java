package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TransportRequestDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.DriverDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    JourneyDto requestJourney(TransportRequestDto transportRequestDto, String trainId);

    JourneyDto endJourney(Long journeyId);

    TrainDto rateTrain(Long journeyId, Integer rating);

    DriverDto getMyProfile();

    Page<JourneyDto> getAllMyJourneys(PageRequest pageRequest);

    Driver createNewDriver(User user);

    Driver getCurrentDriver();
}
