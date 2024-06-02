package org.xproce.testgpt.metier;
import org.xproce.testgpt.entities.*;
import org.xproce.testgpt.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleService salleService;

    public boolean reserveSalle(LocalDate date, LocalTime debut, LocalTime fin, TypeReunion typeReunion, int nombrePersonnes) {
        // Check if fin is exactly one hour after debut
        if (!fin.equals(debut.plusHours(1))) {
            throw new IllegalArgumentException("La fin doit être exactement une heure après le début.");
        }
        // Check if debut is between 8h and 19h
        if (debut.isBefore(LocalTime.of(8, 0)) || debut.isAfter(LocalTime.of(19, 0))) {
            throw new IllegalArgumentException("L'heure de début doit être entre 8h00 et 19h00.");
        }

        List<Salle> availableSalles = salleService.getAvailableSalles(typeReunion, nombrePersonnes);
        for (Salle salle : availableSalles) {
            if (isSalleAvailableWithBuffer(salle, date, debut, fin)) {
                Reservation reservation = new Reservation();
                reservation.setSalle(salle);reservation.setDate(date);reservation.setDebut(debut);reservation.setFin(fin);reservation.setTypeReunion(typeReunion);reservation.setNombrePersonnes(nombrePersonnes);
                reservationRepository.save(reservation);
                return true;
            }
        }
        return false;
    }

    private boolean isSalleAvailableWithBuffer(Salle salle, LocalDate date, LocalTime debut, LocalTime fin) {
        LocalTime bufferStart = debut.minusHours(1);
        LocalTime bufferEnd = fin.plusHours(1);
        List<Reservation> reservations = reservationRepository.findByDateAndSalle(date, salle);
        for (Reservation reservation : reservations) {
            if (overlaps(reservation.getDebut().minusHours(1), reservation.getFin().plusHours(1), bufferStart, bufferEnd)) {
                return false;
            }
        }
        return true;
    }

    private boolean overlaps(LocalTime debut1, LocalTime fin1, LocalTime debut2, LocalTime fin2) {
        return debut1.isBefore(fin2) && debut2.isBefore(fin1);
    }
}
