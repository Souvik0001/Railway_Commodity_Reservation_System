package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Journey;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface JourneyService {

    Journey getJourneyById(Long rideId);

    Journey createNewJourney(JourneyRequest journeyRequest, Train train);

    Journey updateJourneyStatus(Journey journey, JourneyStatus journeyStatus);

    Page<Journey> getAllJourneysOfDriver(Driver driver, PageRequest pageRequest);

    Page<Journey> getAllJourneysOfDriver(Train train, PageRequest pageRequest);
}
