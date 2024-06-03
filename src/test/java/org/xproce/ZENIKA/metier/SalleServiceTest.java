package org.xproce.ZENIKA.metier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xproce.ZENIKA.dao.entities.Equipement;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.dao.repositories.SalleRepository;

class SalleServiceTest {

    @InjectMocks
    private SalleService salleService;

    @Mock
    private SalleRepository salleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableSallesSuccess() {
        Salle salle1 = new Salle();
        salle1.setCapaciteCovid(10);
        salle1.setEquipements(Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE, Equipement.WEBCAM));

        Salle salle2 = new Salle();
        salle2.setCapaciteCovid(12);
        salle2.setEquipements(Collections.singletonList(Equipement.TABLEAU));

        when(salleRepository.findByCapaciteCovidGreaterThanEqual(10)).thenReturn(Arrays.asList(salle1, salle2));

        List<Salle> result = salleService.getAvailableSalles(TypeReunion.VC, 10);

        assertEquals(1, result.size());
        assertTrue(result.contains(salle1));
        assertFalse(result.contains(salle2));
    }

    @Test
    void testGetAvailableSallesNoMatch() {
        Salle salle1 = new Salle();
        salle1.setCapaciteCovid(10);
        salle1.setEquipements(Collections.singletonList(Equipement.TABLEAU));

        when(salleRepository.findByCapaciteCovidGreaterThanEqual(10)).thenReturn(Collections.singletonList(salle1));

        List<Salle> result = salleService.getAvailableSalles(TypeReunion.VC, 10);

        assertTrue(result.isEmpty());
    }

    @Test
    void testEquipementsRequisVC() {
        List<Equipement> result = salleService.equipementsRequis(TypeReunion.VC);
        List<Equipement> expected = Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE, Equipement.WEBCAM);
        assertEquals(expected, result);
    }

    @Test
    void testEquipementsRequisSPEC() {
        List<Equipement> result = salleService.equipementsRequis(TypeReunion.SPEC);
        List<Equipement> expected = Collections.singletonList(Equipement.TABLEAU);
        assertEquals(expected, result);
    }

    @Test
    void testEquipementsRequisRS() {
        List<Equipement> result = salleService.equipementsRequis(TypeReunion.RS);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEquipementsRequisRC() {
        List<Equipement> result = salleService.equipementsRequis(TypeReunion.RC);
        List<Equipement> expected = Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE, Equipement.TABLEAU);
        assertEquals(expected, result);
    }
}
