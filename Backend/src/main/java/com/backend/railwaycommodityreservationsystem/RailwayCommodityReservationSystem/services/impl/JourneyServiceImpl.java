package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Journey;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyRequestStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyRequestService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JourneyServiceImpl implements JourneyService {

    private final JourneyRepository journeyRepository;
    private final JourneyRequestService journeyRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Journey getRideById(Long rideId) {
        return journeyRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: "+rideId));
    }


    @Override
    public Journey createNewRide(JourneyRequest journeyRequest, Train train) {
        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.CONFIRMED);

        Journey journey = modelMapper.map(journeyRequest, Journey.class);
        journey.setJourneyStatus(JourneyStatus.CONFIRMED);
        journey.setTrain(train);
        journey.setStartedAt(LocalDateTime.now());
//        ride.setOtp(generateRandomOTP());
//        ride.setId(generateRandomId());

        journeyRequestService.update(journeyRequest);
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateRideStatus(Journey journey, JourneyStatus journeyStatus) {
        journey.setJourneyStatus(journeyStatus);
        return journeyRepository.save(journey);
    }

    @Override
    public Page<Journey> getAllRidesOfRider(Driver driver, PageRequest pageRequest) {
        return journeyRepository.findByRider(driver, pageRequest);
    }

    @Override
    public Page<Journey> getAllRidesOfDriver(Train train, PageRequest pageRequest) {
        return journeyRepository.findByCycle(train, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
