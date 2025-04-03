package com.pedalup.hackathon.pedalupApp.repositories;

import com.pedalup.hackathon.pedalupApp.entities.Payment;
import com.pedalup.hackathon.pedalupApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRide(Ride ride);
}
