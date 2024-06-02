package org.xproce.ZENIKA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.ZENIKA.entities.Reservation;
import org.xproce.ZENIKA.entities.Salle;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateAndSalle(LocalDate date, Salle salle);
}

