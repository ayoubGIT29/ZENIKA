//package org.xproce.ZENIKA.metier;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.xproce.ZENIKA.dao.entities.Equipement;
//import org.xproce.ZENIKA.exception.InvalidTimeException;
//import org.xproce.ZENIKA.exception.UnavailableSalleException;
//import org.xproce.ZENIKA.dao.entities.Reservation;
//import org.xproce.ZENIKA.dao.entities.Salle;
//import org.xproce.ZENIKA.dao.entities.TypeReunion;
//import org.xproce.ZENIKA.dao.repositories.ReservationRepository;
//import org.xproce.ZENIKA.dao.repositories.SalleRepository;
//
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test") // Use a test profile to avoid interfering with production data
//public class ReservationServiceTest {
//
//    @Autowired
//    private ReservationService reservationService;
//
//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Autowired
//    private SalleRepository salleRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // Clean up the repositories before each test
//        reservationRepository.deleteAll();
//        salleRepository.deleteAll();
//
//        // Add test data to the repository
//        Salle e1001 = new Salle("E1001", 23, Collections.emptyList());
//
//        salleRepository.save(e1001);
//    }
//
//    @Test
//    public void testReserveSalle_Success() {
//        LocalDate date = LocalDate.of(2024, 6, 1);
//        LocalTime debut = LocalTime.of(9, 0);
//        LocalTime fin = LocalTime.of(10, 0);
//        TypeReunion typeReunion = TypeReunion.VC;
//        int nombrePersonnes = 8;
//
//        boolean success = reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);
//
//        assertTrue(success);
//    }
//
//    @Test
//    public void testReserveSalle_InvalidTime() {
//        LocalDate date = LocalDate.of(2024, 6, 1);
//        LocalTime debut = LocalTime.of(7, 0); // Invalid start time
//        LocalTime fin = LocalTime.of(8, 0);
//        TypeReunion typeReunion = TypeReunion.VC;
//        int nombrePersonnes = 8;
//
//        InvalidTimeException exception = assertThrows(InvalidTimeException.class, () ->
//                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
//        );
//
//        assertEquals("L'heure de début doit être entre 8h00 et 19h00.", exception.getMessage());
//    }
//
//    @Test
//    public void testReserveSalle_UnavailableSalle() {
//        LocalDate date = LocalDate.of(2024, 6, 1);
//        LocalTime debut = LocalTime.of(9, 0);
//        LocalTime fin = LocalTime.of(10, 0);
//        TypeReunion typeReunion = TypeReunion.VC;
//        int nombrePersonnes = 8;
//
//        // Create a conflicting reservation
//        List<Salle> salles = salleRepository.findByNom("E3001");
//        assertFalse(salles.isEmpty(), "Salle should exist in the repository");
//        Salle salle = salles.get(0);
//        reservationRepository.save(new Reservation(null, date, LocalTime.of(8, 0), LocalTime.of(9, 0), typeReunion, nombrePersonnes, salle));
//
//        UnavailableSalleException exception = assertThrows(UnavailableSalleException.class, () ->
//                reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes)
//        );
//
//        assertEquals("Salle non disponible pour la réservation demandée.", exception.getMessage());
//    }
//}
