package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyRequestDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.DriverDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.*;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyRequestStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.RuntimeConflictException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.TrainRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRequestRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.DriverRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.TrainService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final ModelMapper modelMapper;
//    private final RideStrategyManager rideStrategyManager;
    private final JourneyRequestRepository journeyRequestRepository;
    private final TrainRepository trainRepository;
    private final DriverRepository driverRepository;
    private final JourneyRepository journeyRepository;
    private final JourneyService journeyService;
    private final TrainService trainService;

    @Override
    @Transactional
    public JourneyDto requestJourney(JourneyRequestDto journeyRequestDto, String trainId) {
        Driver driver = getCurrentDriver();
        JourneyRequest journeyRequest = modelMapper.map(journeyRequestDto, JourneyRequest.class);
        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.PENDING);
        journeyRequest.setDriver(driver);

//        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
//        Double fare = rideRequestDto.getFare();
//        rideRequest.setFare(fare);

        JourneyRequest savedJourneyRequest = journeyRequestRepository.save(journeyRequest);

        Train train = trainRepository.findByTrainId(trainId).orElse(null);

        System.out.print(train);

        if(train == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+trainId);

        if(train.getAvailable() == Boolean.FALSE){
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is not available with cycle Id: "+trainId);
        }

        train.setAvailable(Boolean.FALSE);

        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.CONFIRMED);
        savedJourneyRequest = journeyRequestRepository.save(journeyRequest);

        Train savedTrain = trainRepository.save(train);

        Journey savedJourney = journeyService.createNewJourney(savedJourneyRequest, savedTrain);

//        List<Cycle> drivers = rideStrategyManager
//                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

//        TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedJourney, JourneyDto.class);
    }

    @Override
    public JourneyDto endJourney(Long journeyId) {
        Driver driver = getCurrentDriver();
        Journey journey = journeyService.getJourneyById(journeyId);

        if(!driver.equals(journey.getDriver())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+journeyId));
        }

        if(!journey.getJourneyStatus().equals(JourneyStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ journey.getJourneyStatus());
        }

        Journey savedJourney = journeyService.updateJourneyStatus(journey, JourneyStatus.ENDED);
        trainService.updateTrainAvailability(journey.getTrain(), true);
        journey.setEndedAt(LocalDateTime.now());
        savedJourney = journeyRepository.save(journey);

        return modelMapper.map(savedJourney, JourneyDto.class);
    }

    @Override
    public TrainDto rateTrain(Long journeyId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<JourneyDto> getAllMyJourneys(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return journeyService.getAllJourneysOfDriver(currentDriver, pageRequest).map(
                journey -> modelMapper.map(journey, JourneyDto.class)
        );
    }

    @Override
    public Driver createNewDriver(User user) {
        Driver driver = Driver
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return driverRepository.save(driver);
    }

    @Override
    public Driver getCurrentDriver() {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()
        ));
    }

}
