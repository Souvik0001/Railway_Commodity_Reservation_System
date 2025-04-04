package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Payment;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByJourney(Journey journey);
}
