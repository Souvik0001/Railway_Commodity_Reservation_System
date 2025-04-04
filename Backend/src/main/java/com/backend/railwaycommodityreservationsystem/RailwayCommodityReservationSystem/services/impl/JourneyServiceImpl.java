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
    public Journey getJourneyById(Long journeyId) {
        return journeyRepository.findById(journeyId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: "+journeyId));
    }


    @Override
    public Journey createNewJourney(JourneyRequest journeyRequest, Train train) {
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
    public Journey updateJourneyStatus(Journey journey, JourneyStatus journeyStatus) {
        journey.setJourneyStatus(journeyStatus);
        return journeyRepository.save(journey);
    }

    @Override
    public Page<Journey> getAllJourneysOfDriver(Driver driver, PageRequest pageRequest) {
        return journeyRepository.findByDriver(driver, pageRequest);
    }

    @Override
    public Page<Journey> getAllJourneysOfDriver(Train train, PageRequest pageRequest) {
        return journeyRepository.findByTrain(train, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
