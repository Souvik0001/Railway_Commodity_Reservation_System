package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Journey;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface JourneyService {

    Journey getRideById(Long rideId);

    Journey createNewRide(JourneyRequest journeyRequest, Train train);

    Journey updateRideStatus(Journey journey, JourneyStatus journeyStatus);

    Page<Journey> getAllRidesOfRider(Driver driver, PageRequest pageRequest);

    Page<Journey> getAllRidesOfDriver(Train train, PageRequest pageRequest);
}
