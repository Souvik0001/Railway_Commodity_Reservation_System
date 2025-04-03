package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TransportRequestDto;
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
    public JourneyDto requestRide(TransportRequestDto transportRequestDto, String cycleId) {
        Driver driver = getCurrentRider();
        JourneyRequest journeyRequest = modelMapper.map(transportRequestDto, JourneyRequest.class);
        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.PENDING);
        journeyRequest.setDriver(driver);

//        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
//        Double fare = rideRequestDto.getFare();
//        rideRequest.setFare(fare);

        JourneyRequest savedJourneyRequest = journeyRequestRepository.save(journeyRequest);

        Train train = trainRepository.findByCycleId(cycleId).orElse(null);

        System.out.print(train);

        if(train == null)
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is null not available with cycle Id: "+cycleId);

        if(train.getAvailable() == Boolean.FALSE){
            throw new RuntimeConflictException("Cannot SetUp Ride, Cycle is not available with cycle Id: "+cycleId);
        }

        train.setAvailable(Boolean.FALSE);

        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.CONFIRMED);
        savedJourneyRequest = journeyRequestRepository.save(journeyRequest);

        Train savedTrain = trainRepository.save(train);

        Journey savedJourney = journeyService.createNewRide(savedJourneyRequest, savedTrain);

//        List<Cycle> drivers = rideStrategyManager
//                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

//        TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedJourney, JourneyDto.class);
    }

    @Override
    public JourneyDto endRide(Long rideId) {
        Driver driver = getCurrentRider();
        Journey journey = journeyService.getRideById(rideId);

        if(!driver.equals(journey.getDriver())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+rideId));
        }

        if(!journey.getJourneyStatus().equals(JourneyStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ journey.getJourneyStatus());
        }

        Journey savedJourney = journeyService.updateRideStatus(journey, JourneyStatus.ENDED);
        trainService.updateCycleAvailability(journey.getTrain(), true);
        journey.setEndedAt(LocalDateTime.now());
        savedJourney = journeyRepository.save(journey);

        return modelMapper.map(savedJourney, JourneyDto.class);
    }

    @Override
    public TrainDto rateCycle(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentRider();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<JourneyDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentRider();
        return journeyService.getAllRidesOfRider(currentDriver, pageRequest).map(
                ride -> modelMapper.map(ride, JourneyDto.class)
        );
    }

    @Override
    public Driver createNewRider(User user) {
        Driver driver = Driver
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return driverRepository.save(driver);
    }

    @Override
    public Driver getCurrentRider() {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()
        ));
    }

}
