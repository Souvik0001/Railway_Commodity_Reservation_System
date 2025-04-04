package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.SignupDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.UserDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.User;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.enums.Role;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.RuntimeConflictException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.UserRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.security.JWTService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DriverService driverService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User mockUser;
    private SignupDto mockSignupDto;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password123");
        mockUser.setRoles(Set.of(Role.DRIVER));

        mockSignupDto = new SignupDto();
        mockSignupDto.setEmail("test@example.com");
        mockSignupDto.setPassword("password123");
    }

    @Test
    void testLogin_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtService.generateAccessToken(mockUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(mockUser)).thenReturn("refresh-token");

        String[] tokens = authService.login("test@example.com", "password123");

        assertNotNull(tokens);
        assertEquals("access-token", tokens[0]);
        assertEquals("refresh-token", tokens[1]);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateAccessToken(mockUser);
        verify(jwtService, times(1)).generateRefreshToken(mockUser);
    }

    @Test
    void testSignup_Success() {
        when(userRepository.findByEmail(mockSignupDto.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(mockSignupDto, User.class)).thenReturn(mockUser);
        when(passwordEncoder.encode(mockSignupDto.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(modelMapper.map(mockUser, UserDto.class)).thenReturn(new UserDto());

        UserDto userDto = authService.signup(mockSignupDto);

        assertNotNull(userDto);

        verify(userRepository, times(1)).findByEmail(mockSignupDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(driverService, times(1)).createNewDriver(any(User.class));
    }

    @Test
    void testSignup_ExistingUserThrowsException() {
        when(userRepository.findByEmail(mockSignupDto.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(RuntimeConflictException.class, () -> authService.signup(mockSignupDto));

        verify(userRepository, times(1)).findByEmail(mockSignupDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRefreshToken_Success() {
        when(jwtService.getUserIdFromToken("valid-refresh-token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(jwtService.generateAccessToken(mockUser)).thenReturn("new-access-token");

        String newAccessToken = authService.refreshToken("valid-refresh-token");

        assertEquals("new-access-token", newAccessToken);

        verify(jwtService, times(1)).getUserIdFromToken("valid-refresh-token");
        verify(userRepository, times(1)).findById(1L);
        verify(jwtService, times(1)).generateAccessToken(mockUser);
    }

    @Test
    void testRefreshToken_UserNotFoundThrowsException() {
        when(jwtService.getUserIdFromToken("valid-refresh-token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.refreshToken("valid-refresh-token"));

        verify(userRepository, times(1)).findById(1L);
        verify(jwtService, never()).generateAccessToken(any(User.class));
    }
}
