package org.xproce.ZENIKA.dao.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xproce.ZENIKA.dao.entities.Reservation;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
    }

    @Test
    void testNoArgsConstructor() {
        Reservation newReservation = new Reservation();
        assertNull(newReservation.getId());
        assertNull(newReservation.getDate());
        assertNull(newReservation.getDebut());
        assertNull(newReservation.getFin());
        assertNull(newReservation.getTypeReunion());
        assertEquals(0, newReservation.getNombrePersonnes());
        assertNull(newReservation.getSalle());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(2023, 6, 1);
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 10;
        Salle salle = new Salle();

        Reservation newReservation = new Reservation(1L, date, debut, fin, typeReunion, nombrePersonnes, salle);

        assertEquals(1L, newReservation.getId());
        assertEquals(date, newReservation.getDate());
        assertEquals(debut, newReservation.getDebut());
        assertEquals(fin, newReservation.getFin());
        assertEquals(typeReunion, newReservation.getTypeReunion());
        assertEquals(nombrePersonnes, newReservation.getNombrePersonnes());
        assertEquals(salle, newReservation.getSalle());
    }

    @Test
    void testGettersAndSetters() {
        LocalDate date = LocalDate.of(2023, 6, 1);
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 10;
        Salle salle = new Salle();

        reservation.setId(1L);
        reservation.setDate(date);
        reservation.setDebut(debut);
        reservation.setFin(fin);
        reservation.setTypeReunion(typeReunion);
        reservation.setNombrePersonnes(nombrePersonnes);
        reservation.setSalle(salle);

        assertEquals(1L, reservation.getId());
        assertEquals(date, reservation.getDate());
        assertEquals(debut, reservation.getDebut());
        assertEquals(fin, reservation.getFin());
        assertEquals(typeReunion, reservation.getTypeReunion());
        assertEquals(nombrePersonnes, reservation.getNombrePersonnes());
        assertEquals(salle, reservation.getSalle());
    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.of(2023, 6, 1);
        LocalTime debut = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(11, 0);
        TypeReunion typeReunion = TypeReunion.VC;
        int nombrePersonnes = 10;
        Salle salle = new Salle();

        reservation = new Reservation(1L, date, debut, fin, typeReunion, nombrePersonnes, salle);
        String expectedToString = "Reservation(id=1, date=2023-06-01, debut=10:00, fin=11:00, typeReunion=VC, nombrePersonnes=10, salle=" + salle + ")";

        assertEquals(expectedToString, reservation.toString());
    }
}
