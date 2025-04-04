package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.controllers;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.JourneyDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TransportRequestDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.TrainService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // Allow only from this origin
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class TrainController {

    private final DriverService driverService;
    private final TrainService trainService;

    @PostMapping("/requestJourney/{trainId}")
    public ResponseEntity<JourneyDto> requestRide(@RequestBody TransportRequestDto transportRequestDto, @PathVariable String trainId) {
        return ResponseEntity.ok(driverService.requestJourney(transportRequestDto,trainId));
    }

//    @PostMapping("/acceptRide/{rideRequestId}")
//    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
//        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
//    }

    @PostMapping("/endRide/{journeyId}")
    public ResponseEntity<JourneyDto> requestRide(@PathVariable Long journeyId) {
        return ResponseEntity.ok(driverService.endJourney(journeyId));
    }

    @PostMapping("/trainLocation/{trainId}")
    public ResponseEntity<TrainDto> updateLocation(@RequestBody TrainDto trainDto, @PathVariable String trainId) {
        return ResponseEntity.ok(trainService.updateLocation(trainDto,trainId));
    }
}
