package org.xproce.ZENIKA.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Salles")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int capaciteMaximale;
    private int capaciteCovid;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Equipement> equipements;

    public Salle(String nom, int capaciteMaximale,List<Equipement>  equipements) {
        this.nom = nom;
        this.capaciteMaximale = capaciteMaximale;
        capaciteCovid=(int)(capaciteMaximale* 0.7);
        this.equipements = equipements;

    }
}
