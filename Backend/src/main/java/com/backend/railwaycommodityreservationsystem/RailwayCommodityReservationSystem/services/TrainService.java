package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;

public interface CycleService {

    Train getCurrentCycle();

    Train updateCycleAvailability(Train train, boolean available);

    TrainDto updateLocation(TrainDto cycleDto, String cycleId);
}
