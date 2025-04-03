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
@RequestMapping("/riders")
@RequiredArgsConstructor
public class TrainController {

    private final DriverService driverService;
    private final TrainService trainService;

    @PostMapping("/requestRide/{cycleId}")
    public ResponseEntity<JourneyDto> requestRide(@RequestBody TransportRequestDto transportRequestDto, @PathVariable String cycleId) {
        return ResponseEntity.ok(driverService.requestRide(transportRequestDto,cycleId));
    }

//    @PostMapping("/acceptRide/{rideRequestId}")
//    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
//        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
//    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<JourneyDto> requestRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cycleLocation/{cycleId}")
    public ResponseEntity<TrainDto> updateLocation(@RequestBody TrainDto cycleDto, @PathVariable String cycleId) {
        return ResponseEntity.ok(trainService.updateLocation(cycleDto,cycleId));
    }
}
