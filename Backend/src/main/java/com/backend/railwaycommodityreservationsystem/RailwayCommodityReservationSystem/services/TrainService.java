package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;

public interface TrainService {

    Train getCurrentTrain();

    Train updateTrainAvailability(Train train, boolean available);

    TrainDto updateLocation(TrainDto trainDto, String trainId);
}
