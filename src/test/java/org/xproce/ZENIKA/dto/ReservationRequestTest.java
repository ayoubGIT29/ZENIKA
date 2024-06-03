package org.xproce.ZENIKA.dto;

import org.junit.jupiter.api.Test;
import org.xproce.ZENIKA.dao.entities.TypeReunion;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationRequestTest {

    @Test
    public void testReservationRequest() {
        // Create a ReservationRequest object
        ReservationRequest reservationRequest = new ReservationRequest();

        // Set values for the fields
        LocalDate date = LocalDate.of(2024, 6, 3);
        reservationRequest.setDate(date);

        LocalTime debut = LocalTime.of(9, 0);
        reservationRequest.setDebut(debut);

        LocalTime fin = LocalTime.of(11, 0);
        reservationRequest.setFin(fin);

        TypeReunion typeReunion = TypeReunion.VC;
        reservationRequest.setTypeReunion(typeReunion);

        int nombrePersonnes = 10;
        reservationRequest.setNombrePersonnes(nombrePersonnes);

        // Verify that getters return the correct values
        assertEquals(date, reservationRequest.getDate());
        assertEquals(debut, reservationRequest.getDebut());
        assertEquals(fin, reservationRequest.getFin());
        assertEquals(typeReunion, reservationRequest.getTypeReunion());
        assertEquals(nombrePersonnes, reservationRequest.getNombrePersonnes());
    }
}
