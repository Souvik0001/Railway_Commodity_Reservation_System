package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;

public interface JourneyRequestService {

    JourneyRequest findJourneyRequestById(Long journeyRequestId);

    void update(JourneyRequest journeyRequest);
}
