package org.xproce.ZENIKA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.xproce.ZENIKA.entities.Equipement;
import org.xproce.ZENIKA.entities.Salle;
import org.xproce.ZENIKA.repositories.SalleRepository;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class TestGptApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestGptApplication.class, args);

        SalleRepository salleRepository = context.getBean(SalleRepository.class);

        // Initialize data
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
}
