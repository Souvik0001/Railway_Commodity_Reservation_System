package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // Allow only from this origin
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> subErrors;
}
