package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.JourneyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRequestRepository extends JpaRepository<JourneyRequest, Long> {
}
