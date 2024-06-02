package org.xproce.testgpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.testgpt.entities.Reservation;
import org.xproce.testgpt.entities.Salle;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateAndSalle(LocalDate date, Salle salle);
}

