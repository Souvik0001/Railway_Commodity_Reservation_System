package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
//    Optional<Cycle> findByCycleId(String cycleId);

    @Query("SELECT c FROM Train c WHERE c.trainId = :trainId")
    Optional<Train> findByTrainId(@Param("trainId") String trainId);
}
