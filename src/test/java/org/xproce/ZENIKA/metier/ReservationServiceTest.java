package org.xproce.ZENIKA.metier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xproce.ZENIKA.dao.entities.Reservation;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.dao.repositories.ReservationRepository;
import org.xproce.ZENIKA.dao.repositories.SalleRepository;
import org.xproce.ZENIKA.exception.InvalidTimeException;
import org.xproce.ZENIKA.exception.UnavailableSalleException;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SalleRepository salleRepository;

    @Mock
    private SalleService salleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReserveSalleSuccess() {
        LocalDate date = LocalDate.now();
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 10;

        Salle salle = new Salle();
        List<Salle> availableSalles = Collections.singletonList(salle);
        when(salleService.getAvailableSalles(typeReunion, nombrePersonnes)).thenReturn(availableSalles);
        when(reservationRepository.findByDateAndSalle(date, salle)).thenReturn(Collections.emptyList());

        boolean result = reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);

        assertTrue(result);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testReserveSalleInvalidTimeException() {
        LocalDate date = LocalDate.now();
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(12, 0);  // Invalid, not exactly 1 hour after debut
        TypeReunion typeReunion = TypeReunion.RC;
        int nombrePersonnes = 10;

        InvalidTimeException exception = assertThrows(InvalidTimeException.class, () -> {
            reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);
        });

        assertEquals("La fin doit être exactement une heure après le début.", exception.getMessage());
    }

    @Test
    void testReserveSalleUnavailableSalleException() {
        LocalDate date = LocalDate.now();
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 10;

        when(salleService.getAvailableSalles(typeReunion, nombrePersonnes)).thenReturn(Collections.emptyList());

        UnavailableSalleException exception = assertThrows(UnavailableSalleException.class, () -> {
            reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);
        });

        assertEquals("Salle non disponible pour la réservation demandée.", exception.getMessage());
    }

    @Test
    void testReserveSalleStartOutsideValidHours() {
        LocalDate date = LocalDate.now();
        LocalTime debut = LocalTime.of(7, 0);  // Invalid, before 8h
        LocalTime fin = LocalTime.of(8, 0);
        TypeReunion typeReunion = TypeReunion.SPEC;
        int nombrePersonnes = 10;

        InvalidTimeException exception = assertThrows(InvalidTimeException.class, () -> {
            reservationService.reserveSalle(date, debut, fin, typeReunion, nombrePersonnes);
        });

        assertEquals("L'heure de début doit être entre 8h00 et 19h00.", exception.getMessage());
    }
}
