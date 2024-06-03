package org.xproce.ZENIKA.metier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.xproce.ZENIKA.dao.entities.Equipement;
import org.xproce.ZENIKA.exception.InvalidTimeException;
import org.xproce.ZENIKA.exception.UnavailableSalleException;
import org.xproce.ZENIKA.dao.entities.Reservation;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.dao.repositories.ReservationRepository;
import org.xproce.ZENIKA.dao.repositories.SalleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();
        salleRepository.deleteAll();

        // Add test data to the repository
        Salle e1001 = new Salle("E1001", 23, Collections.emptyList());
        Salle e1002 = new Salle("E1002", 10, Arrays.asList(Equipement.ECRAN));
        Salle e1003 = new Salle("E1003", 8, Arrays.asList(Equipement.PIEUVRE));
        Salle e1004 = new Salle("E1004", 4, Arrays.asList(Equipement.TABLEAU));
        Salle e2001 = new Salle("E2001", 4, Collections.emptyList());
        Salle e2002 = new Salle("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM));
        Salle e2003 = new Salle("E2003", 7, Collections.emptyList());
        Salle e2004 = new Salle("E2004", 9, Arrays.asList(Equipement.TABLEAU));
        Salle e3001 = new Salle("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE));
        Salle e3002 = new Salle("E3002", 8, Collections.emptyList());
        Salle e3003 = new Salle("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE));
        Salle e3004 = new Salle("E3004", 4, Collections.emptyList());

        salleRepository.saveAll(Arrays.asList(e1001, e1002, e1003, e1004, e2001, e2002, e2003, e2004, e3001, e3002, e3003, e3004));
    }

    @Test
    public void testReserveSalle_Success() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        LocalTime debut = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(10, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 8;

        boolean success = reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);

        assertEquals(true, success);
    }

    @Test
    public void testReserveSalle_InvalidTime() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        LocalTime debut = LocalTime.of(7, 0); // Invalid start time
        LocalTime fin = LocalTime.of(8, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 8;

        InvalidTimeException exception = assertThrows(InvalidTimeException.class, () ->
                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
        );

        assertEquals("L'heure de début doit être entre 8h00 et 19h00.", exception.getMessage());
    }

    @Test
    public void testReserveSalle_UnavailableSalle() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        LocalTime debut = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(10, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 8;

        // Create a conflicting reservation
        List<Salle> salles = salleRepository.findByNom("E3001");
        assertFalse(salles.isEmpty(), "Salle should exist in the repository");
        Salle salle = salles.get(0);
        reservationRepository.save(new Reservation(null, date, LocalTime.of(8, 0), LocalTime.of(9, 0), typeReunion, nombrePersonnes, salle));

        UnavailableSalleException exception = assertThrows(UnavailableSalleException.class, () ->
                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
        );

        assertEquals("Salle non disponible pour la réservation demandée.", exception.getMessage());
    }

    @Test
    public void testReserveSalleForRC_FAIL() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        LocalTime debut = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(10, 0);
        TypeReunion typeReunion = TypeReunion.RC;
        int nombrePersonnes = 8;

        UnavailableSalleException exception = assertThrows(UnavailableSalleException.class, () ->
                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
        );

        assertEquals("Salle non disponible pour la réservation demandée.", exception.getMessage());
    }

    @Test
    public void testReserveSalle_UnavailableSallePlus1H() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 8;

        // Create a conflicting reservation
        List<Salle> salles = salleRepository.findByNom("E3001");
        assertFalse(salles.isEmpty(), "Salle should exist in the repository");
        Salle salle = salles.get(0);
        reservationRepository.save(new Reservation(null, date, LocalTime.of(9, 0), LocalTime.of(10, 0), typeReunion, nombrePersonnes, salle));

        UnavailableSalleException exception = assertThrows(UnavailableSalleException.class, () ->
                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
        );

        assertEquals("Salle non disponible pour la réservation demandée.", exception.getMessage());
    }
}
