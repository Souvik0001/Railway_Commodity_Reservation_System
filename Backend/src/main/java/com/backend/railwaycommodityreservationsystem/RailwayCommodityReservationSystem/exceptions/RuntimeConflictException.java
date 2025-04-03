package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions;

public class RuntimeConflictException extends RuntimeException{

    public RuntimeConflictException() {
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}
