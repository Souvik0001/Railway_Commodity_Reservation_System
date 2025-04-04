package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.RuntimeConflictException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.TrainRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.TrainService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyRequestService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final JourneyRequestService journeyRequestService;
    private final TrainRepository trainRepository;
    private final JourneyService journeyService;
    private final ModelMapper modelMapper;


    @Override
    public Train getCurrentTrain() {
        return trainRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Cycle not found with " +
                "id "+2));
    }

    @Override
    public Train updateTrainAvailability(Train train, boolean available) {
        train.setAvailable(available);
        return trainRepository.save(train);
    }

    @Override
    public TrainDto updateLocation(TrainDto trainDto, String trainId){
        Train train = trainRepository.findByTrainId(trainId).orElse(null);
        if(train == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+trainId);
//        cycle.setCurrentLocation(modelMapper.map(pointDto, Cycle.class));
        Train updatedTrain = modelMapper.map(trainDto, Train.class);

        train.setCurrentLocation(updatedTrain.getCurrentLocation());

//        return cycleRepository.save(updatedCycle);


        return modelMapper.map(trainRepository.save(train), TrainDto.class);
    }
}
