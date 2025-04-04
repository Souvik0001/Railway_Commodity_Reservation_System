package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.JourneyRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JourneyRequestServiceImplTest {

    @Mock
    private JourneyRequestRepository journeyRequestRepository;

    @InjectMocks
    private JourneyRequestServiceImpl journeyRequestService;

    private JourneyRequest journeyRequest;

    @BeforeEach
    void setUp() {
        journeyRequest = new JourneyRequest();
        journeyRequest.setId(1L);
    }

    @Test
    void testFindJourneyRequestById_Found() {

        when(journeyRequestRepository.findById(1L)).thenReturn(Optional.of(journeyRequest));

        JourneyRequest foundRequest = journeyRequestService.findJourneyRequestById(1L);

        assertNotNull(foundRequest);
        assertEquals(1L, foundRequest.getId());
        verify(journeyRequestRepository, times(1)).findById(1L);
    }

    @Test
    void testFindJourneyRequestById_NotFound() {

        when(journeyRequestRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                journeyRequestService.findJourneyRequestById(1L));

        assertEquals("RideRequest not found with id: 1", exception.getMessage());
        verify(journeyRequestRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate_Success() {

        when(journeyRequestRepository.findById(1L)).thenReturn(Optional.of(journeyRequest));


        when(journeyRequestRepository.save(journeyRequest)).thenReturn(journeyRequest);

        assertDoesNotThrow(() -> journeyRequestService.update(journeyRequest));

        verify(journeyRequestRepository, times(1)).findById(1L);
        verify(journeyRequestRepository, times(1)).save(journeyRequest);
    }

    @Test
    void testUpdate_NotFound() {

        when(journeyRequestRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                journeyRequestService.update(journeyRequest));

        assertEquals("RideRequest not found with id: 1", exception.getMessage());
        verify(journeyRequestRepository, times(1)).findById(1L);
        verify(journeyRequestRepository, never()).save(any(JourneyRequest.class));
    }
}
