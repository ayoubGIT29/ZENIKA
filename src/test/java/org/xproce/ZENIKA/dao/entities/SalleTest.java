package org.xproce.ZENIKA.dao.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xproce.ZENIKA.dao.entities.Equipement;
import org.xproce.ZENIKA.dao.entities.Salle;

import java.util.Arrays;
import java.util.List;

class SalleTest {

    private Salle salle;

    @BeforeEach
    void setUp() {
        salle = new Salle();
    }

    @Test
    void testNoArgsConstructor() {
        Salle newSalle = new Salle();
        assertNull(newSalle.getId());
        assertNull(newSalle.getNom());
        assertEquals(0, newSalle.getCapaciteMaximale());
        assertEquals(0, newSalle.getCapaciteCovid());
        assertNull(newSalle.getEquipements());
    }

    @Test
    void testAllArgsConstructor() {
        String nom = "Salle A";
        int capaciteMaximale = 100;
        List<Equipement> equipements = Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE);

        Salle newSalle = new Salle(nom, capaciteMaximale, equipements);

        assertNull(newSalle.getId());
        assertEquals(nom, newSalle.getNom());
        assertEquals(capaciteMaximale, newSalle.getCapaciteMaximale());
        assertEquals((int) (capaciteMaximale * 0.7), newSalle.getCapaciteCovid());
        assertEquals(equipements, newSalle.getEquipements());
    }

    @Test
    void testGettersAndSetters() {
        String nom = "Salle B";
        int capaciteMaximale = 80;
        List<Equipement> equipements = Arrays.asList(Equipement.WEBCAM, Equipement.TABLEAU);

        salle.setId(1L);
        salle.setNom(nom);
        salle.setCapaciteMaximale(capaciteMaximale);
        salle.setCapaciteCovid((int) (capaciteMaximale * 0.7));
        salle.setEquipements(equipements);

        assertEquals(1L, salle.getId());
        assertEquals(nom, salle.getNom());
        assertEquals(capaciteMaximale, salle.getCapaciteMaximale());
        assertEquals((int) (capaciteMaximale * 0.7), salle.getCapaciteCovid());
        assertEquals(equipements, salle.getEquipements());
    }

    @Test
    void testToString() {
        String nom = "Salle C";
        int capaciteMaximale = 120;
        List<Equipement> equipements = Arrays.asList(Equipement.WEBCAM, Equipement.ECRAN, Equipement.TABLEAU);

        salle = new Salle(nom, capaciteMaximale, equipements);
        salle.setId(1L);
        salle.setCapaciteCovid((int) (capaciteMaximale * 0.7));
        String expectedToString = "Salle(id=1, nom=Salle C, capaciteMaximale=120, capaciteCovid=84, equipements=[WEBCAM, ECRAN, TABLEAU])";

        assertEquals(expectedToString, salle.toString());
    }
}
