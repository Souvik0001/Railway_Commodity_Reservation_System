package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.RuntimeConflictException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.TrainRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.CycleService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.RideRequestService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CycleServiceImpl implements CycleService {

    private final RideRequestService rideRequestService;
    private final TrainRepository trainRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;


    @Override
    public Train getCurrentCycle() {
        return trainRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Cycle not found with " +
                "id "+2));
    }

    @Override
    public Train updateCycleAvailability(Train train, boolean available) {
        train.setAvailable(available);
        return trainRepository.save(train);
    }

    @Override
    public TrainDto updateLocation(TrainDto cycleDto, String cycleId){
        Train train = trainRepository.findByCycleId(cycleId).orElse(null);
        if(train == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+cycleId);
//        cycle.setCurrentLocation(modelMapper.map(pointDto, Cycle.class));
        Train updatedTrain = modelMapper.map(cycleDto, Train.class);

        train.setCurrentLocation(updatedTrain.getCurrentLocation());

//        return cycleRepository.save(updatedCycle);


        return modelMapper.map(trainRepository.save(train), TrainDto.class);
    }
}
