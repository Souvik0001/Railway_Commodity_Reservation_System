package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
//    Optional<Cycle> findByCycleId(String cycleId);

    @Query("SELECT c FROM Cycle c WHERE c.cycleId = :cycleId")
    Optional<Cycle> findByCycleId(@Param("cycleId") String cycleId);
}
