package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Journey;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    Page<Journey> findByDriver(Driver driver, Pageable pageRequest);

    Page<Journey> findByTrain(Train train, Pageable pageRequest);
}
