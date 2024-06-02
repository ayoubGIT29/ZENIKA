package org.xproce.testgpt.dto;

import lombok.Getter;
import lombok.Setter;
import org.xproce.testgpt.entities.TypeReunion;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequest {
    private LocalDate date;
    private LocalTime debut;
    private LocalTime fin;
    private TypeReunion typeReunion;
    private int nombrePersonnes;

    // Getters and setters
}
