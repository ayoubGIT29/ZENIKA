package org.xproce.ZENIKA.dao.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime debut;
    private LocalTime fin;
    @Enumerated(EnumType.STRING)
    private TypeReunion typeReunion;
    private int nombrePersonnes;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;


}
