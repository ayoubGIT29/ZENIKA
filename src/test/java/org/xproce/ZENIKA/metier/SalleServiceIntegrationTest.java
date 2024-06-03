package org.xproce.ZENIKA.metier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.xproce.ZENIKA.dao.entities.Equipement;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.dao.repositories.SalleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SalleServiceIntegrationTest {

    @Autowired
    private SalleService salleService;

    @Autowired
    private SalleRepository salleRepository;

    @BeforeEach
    public void setUp() {
        salleRepository.deleteAll();

        // Add test data to the repository
        Salle e1001 = new Salle("E1001", 23, Collections.emptyList());
        Salle e1002 = new Salle("E1002", 10, Arrays.asList(Equipement.ECRAN));
        Salle e1003 = new Salle("E1003", 8, Arrays.asList(Equipement.PIEUVRE));
        Salle e1004 = new Salle("E1004", 4, Arrays.asList(Equipement.TABLEAU));
        Salle e2001 = new Salle("E2001", 4, Collections.emptyList());
        Salle e2002 = new Salle("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM));
        Salle e2003 = new Salle("E2003", 7, Collections.emptyList());
        Salle e2004 = new Salle("E2004", 9, Arrays.asList(Equipement.TABLEAU));
        Salle e3001 = new Salle("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE));
        Salle e3002 = new Salle("E3002", 8, Collections.emptyList());
        Salle e3003 = new Salle("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE));
        Salle e3004 = new Salle("E3004", 4, Collections.emptyList());

        salleRepository.saveAll(Arrays.asList(e1001, e1002, e1003, e1004, e2001, e2002, e2003, e2004, e3001, e3002, e3003, e3004));
    }

    @Test
    public void testGetAvailableSalles_Success() {
        List<Salle> availableSalles = salleService.getAvailableSalles(TypeReunion.VC, 8);

        assertNotNull(availableSalles);
        assertFalse(availableSalles.isEmpty());
    }

    @Test
    public void testGetAvailableSalles_NoMatches() {
        List<Salle> availableSalles = salleService.getAvailableSalles(TypeReunion.SPEC, 25);

        assertNotNull(availableSalles);
        assertTrue(availableSalles.isEmpty());
    }
}
