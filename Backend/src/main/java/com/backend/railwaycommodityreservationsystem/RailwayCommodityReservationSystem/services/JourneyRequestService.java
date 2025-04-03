package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;

public interface RideRequestService {

    JourneyRequest findRideRequestById(Long rideRequestId);

    void update(JourneyRequest journeyRequest);
}
