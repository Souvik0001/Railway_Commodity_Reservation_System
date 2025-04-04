package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.*;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyRequestStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.JourneyStatus;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.JourneyRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JourneyServiceImplTest {

    @Mock
    private JourneyRepository journeyRepository;

    @Mock
    private JourneyRequestService journeyRequestService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private JourneyServiceImpl journeyService;

    private Journey journey;
    private JourneyRequest journeyRequest;
    private Train train;
    private Driver driver;

    @BeforeEach
    void setUp() {
        train = new Train();
        train.setId(1L);

        driver = new Driver();
        driver.setId(1L);

        journeyRequest = new JourneyRequest();
        journeyRequest.setId(1L);
        journeyRequest.setJourneyRequestStatus(JourneyRequestStatus.PENDING);

        journey = new Journey();
        journey.setId(1L);
        journey.setTrain(train);
        journey.setJourneyStatus(JourneyStatus.CONFIRMED);
        journey.setStartedAt(LocalDateTime.now());
    }

    @Test
    void testGetJourneyById_Found() {
        when(journeyRepository.findById(1L)).thenReturn(Optional.of(journey));

        Journey foundJourney = journeyService.getJourneyById(1L);

        assertNotNull(foundJourney);
        assertEquals(1L, foundJourney.getId());
        verify(journeyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetJourneyById_NotFound() {
        when(journeyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                journeyService.getJourneyById(1L));

        assertEquals("Ride not found with id: 1", exception.getMessage());
        verify(journeyRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateNewJourney_Success() {
        Journey mappedJourney = new Journey();
        mappedJourney.setTrain(train);
        mappedJourney.setJourneyStatus(JourneyStatus.CONFIRMED);
        mappedJourney.setStartedAt(LocalDateTime.now());

        when(modelMapper.map(journeyRequest, Journey.class)).thenReturn(mappedJourney);
        when(journeyRepository.save(any(Journey.class))).thenReturn(mappedJourney);

        Journey createdJourney = journeyService.createNewJourney(journeyRequest, train);

        assertNotNull(createdJourney);
        assertEquals(train, createdJourney.getTrain());
        assertEquals(JourneyStatus.CONFIRMED, createdJourney.getJourneyStatus());

        verify(journeyRequestService, times(1)).update(journeyRequest);
        verify(journeyRepository, times(1)).save(any(Journey.class));
    }

    @Test
    void testUpdateJourneyStatus_Success() {
        when(journeyRepository.save(any(Journey.class))).thenReturn(journey);

        Journey updatedJourney = journeyService.updateJourneyStatus(journey, JourneyStatus.ENDED);

        assertNotNull(updatedJourney);
        assertEquals(JourneyStatus.ENDED, updatedJourney.getJourneyStatus());

        verify(journeyRepository, times(1)).save(journey);
    }

    @Test
    void testGetAllJourneysOfDriver_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Journey> page = new PageImpl<>(List.of(journey));

        when(journeyRepository.findByDriver(driver, pageRequest)).thenReturn(page);

        Page<Journey> result = journeyService.getAllJourneysOfDriver(driver, pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(journeyRepository, times(1)).findByDriver(driver, pageRequest);
    }

    @Test
    void testGetAllJourneysOfTrain_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Journey> page = new PageImpl<>(List.of(journey));

        when(journeyRepository.findByTrain(train, pageRequest)).thenReturn(page);

        Page<Journey> result = journeyService.getAllJourneysOfDriver(train, pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(journeyRepository, times(1)).findByTrain(train, pageRequest);
    }
}
