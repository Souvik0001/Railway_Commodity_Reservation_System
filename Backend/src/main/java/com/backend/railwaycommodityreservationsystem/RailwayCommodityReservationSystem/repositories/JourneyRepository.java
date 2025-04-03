package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Cycle;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Ride;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findByRider(Rider rider, Pageable pageRequest);

    Page<Ride> findByCycle(Cycle cycle, Pageable pageRequest);
}
