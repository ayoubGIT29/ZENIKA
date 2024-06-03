package org.xproce.ZENIKA.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.xproce.ZENIKA.dto.ReservationRequest;
import org.xproce.ZENIKA.exception.InvalidTimeException;
import org.xproce.ZENIKA.exception.UnavailableSalleException;
import org.xproce.ZENIKA.metier.ReservationService;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xproce.ZENIKA.web.ReservationController;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testReserveSalleSuccess() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setDate(LocalDate.now());
        reservationRequest.setDebut(LocalTime.of(10, 0));
        reservationRequest.setFin(LocalTime.of(11, 0));
        reservationRequest.setTypeReunion(TypeReunion.VC);
        reservationRequest.setNombrePersonnes(10);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Réservation réussie"));

        verify(reservationService, times(1)).reserveSalle(
                reservationRequest.getDate(),
                reservationRequest.getDebut(),
                reservationRequest.getFin(),
                reservationRequest.getTypeReunion(),
                reservationRequest.getNombrePersonnes()
        );
    }

    @Test
    void testReserveSalleInvalidTimeException() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setDate(LocalDate.now());
        reservationRequest.setDebut(LocalTime.of(10, 0));
        reservationRequest.setFin(LocalTime.of(12, 0));  // Invalid, not exactly 1 hour after debut
        reservationRequest.setTypeReunion(TypeReunion.VC);
        reservationRequest.setNombrePersonnes(10);

        doThrow(new InvalidTimeException("La fin doit être exactement une heure après le début."))
                .when(reservationService).reserveSalle(
                        reservationRequest.getDate(),
                        reservationRequest.getDebut(),
                        reservationRequest.getFin(),
                        reservationRequest.getTypeReunion(),
                        reservationRequest.getNombrePersonnes()
                );

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La fin doit être exactement une heure après le début."));

        verify(reservationService, times(1)).reserveSalle(
                reservationRequest.getDate(),
                reservationRequest.getDebut(),
                reservationRequest.getFin(),
                reservationRequest.getTypeReunion(),
                reservationRequest.getNombrePersonnes()
        );
    }

    @Test
    void testReserveSalleUnavailableSalleException() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setDate(LocalDate.now());
        reservationRequest.setDebut(LocalTime.of(10, 0));
        reservationRequest.setFin(LocalTime.of(11, 0));
        reservationRequest.setTypeReunion(TypeReunion.VC);
        reservationRequest.setNombrePersonnes(10);

        doThrow(new UnavailableSalleException("Salle non disponible pour la réservation demandée."))
                .when(reservationService).reserveSalle(
                        reservationRequest.getDate(),
                        reservationRequest.getDebut(),
                        reservationRequest.getFin(),
                        reservationRequest.getTypeReunion(),
                        reservationRequest.getNombrePersonnes()
                );

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Salle non disponible pour la réservation demandée."));

        verify(reservationService, times(1)).reserveSalle(
                reservationRequest.getDate(),
                reservationRequest.getDebut(),
                reservationRequest.getFin(),
                reservationRequest.getTypeReunion(),
                reservationRequest.getNombrePersonnes()
        );
    }
}
