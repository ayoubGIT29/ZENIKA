package org.xproce.ZENIKA.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xproce.ZENIKA.dao.entities.Reservation;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.dao.repositories.ReservationRepository;
import org.xproce.ZENIKA.dao.repositories.SalleRepository;

import org.xproce.ZENIKA.exception.InvalidTimeException;
import org.xproce.ZENIKA.exception.UnavailableSalleException;

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
            throw new InvalidTimeException("La fin doit être exactement une heure après le début.");
        }

        if (debut.isBefore(LocalTime.of(8, 0)) || debut.isAfter(LocalTime.of(19, 0))) {
            throw new InvalidTimeException("L'heure de début doit être entre 8h00 et 19h00.");
        }

        List<Salle> availableSalles = salleService.getAvailableSalles(typeReunion, nombrePersonnes);
        for (Salle salle : availableSalles) {
            if (isSalleAvailableWithBuffer(salle, date, debut, fin)) {
                Reservation reservation = new Reservation(null,date, debut, fin, typeReunion, nombrePersonnes,salle);
                reservationRepository.save(reservation);
                return true;
            }
        }
        throw new UnavailableSalleException("Salle non disponible pour la réservation demandée.");
    }

    private boolean isSalleAvailableWithBuffer(Salle salle, LocalDate date, LocalTime debut, LocalTime fin) {
        LocalTime bufferStart = debut.minusHours(1);
        LocalTime bufferEnd = fin.plusHours(1);

        List<Reservation> reservations = reservationRepository.findByDateAndSalle(date, salle);
        for (Reservation reservation : reservations) {
            LocalTime resDebut = reservation.getDebut();
            LocalTime resFin = reservation.getFin();

            if (bufferStart.isBefore(resFin) && bufferEnd.isAfter(resDebut)) {
                return false;
            }
        }
        return true;
    }
}
