package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.controllers;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.CycleDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.RideDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.RideRequestDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.CycleService;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // Allow only from this origin
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final CycleService cycleService;

    @PostMapping("/requestRide/{cycleId}")
    public ResponseEntity<RideDto> requestRide(@RequestBody RideRequestDto rideRequestDto, @PathVariable String cycleId) {
        return ResponseEntity.ok(riderService.requestRide(rideRequestDto,cycleId));
    }

//    @PostMapping("/acceptRide/{rideRequestId}")
//    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
//        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
//    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> requestRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(riderService.endRide(rideId));
    }

    @PostMapping("/cycleLocation/{cycleId}")
    public ResponseEntity<CycleDto> updateLocation(@RequestBody CycleDto cycleDto, @PathVariable String cycleId) {
        return ResponseEntity.ok(cycleService.updateLocation(cycleDto,cycleId));
    }
}
