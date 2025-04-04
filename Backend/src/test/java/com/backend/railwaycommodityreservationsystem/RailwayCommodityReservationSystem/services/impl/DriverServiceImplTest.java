package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.User;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.DriverRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private DriverServiceImpl driverService;

    private User testUser;
    private Driver testDriver;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testDriver = new Driver();
        testDriver.setId(1L);
        testDriver.setUser(testUser);

        // Mock security context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(testUser.getEmail());
        SecurityContextHolder.setContext(securityContext);

        // Ensure loadUserByUsername returns a valid User
        doReturn(testUser).when(userService).loadUserByUsername(testUser.getEmail());
    }

    @Test
    void testGetCurrentDriver_Found() {
        // ✅ Use `any(User.class)` to avoid strict stubbing issues
        doReturn(Optional.of(testDriver)).when(driverRepository).findByUser(any(User.class));

        Driver foundDriver = driverService.getCurrentDriver();

        assertNotNull(foundDriver);
        assertEquals(testDriver.getId(), foundDriver.getId());
        assertEquals(testDriver.getUser().getEmail(), foundDriver.getUser().getEmail());

        verify(driverRepository, times(1)).findByUser(any(User.class));
    }

    @Test
    void testGetCurrentDriver_NotFound() {
        // ✅ Ensure method is stubbed properly
        doReturn(Optional.empty()).when(driverRepository).findByUser(any(User.class));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> driverService.getCurrentDriver());

        assertEquals("Rider not associated with user with id: " + testUser.getId(), exception.getMessage());
    }
}
