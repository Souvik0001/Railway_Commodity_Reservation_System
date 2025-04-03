package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRequestRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JourneyRequestServiceImpl implements JourneyRequestService {

    private final JourneyRequestRepository journeyRequestRepository;

    @Override
    public JourneyRequest findRideRequestById(Long rideRequestId) {
        return journeyRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequestId));
    }

    @Override
    public void update(JourneyRequest journeyRequest) {
        journeyRequestRepository.findById(journeyRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+ journeyRequest.getId()));
        journeyRequestRepository.save(journeyRequest);
    }
}
