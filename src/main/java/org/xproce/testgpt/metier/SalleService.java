package org.xproce.testgpt.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xproce.testgpt.entities.Equipement;
import org.xproce.testgpt.entities.Salle;
import org.xproce.testgpt.entities.TypeReunion;
import org.xproce.testgpt.repositories.SalleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalleService {
    @Autowired
    private SalleRepository salleRepository;

    public List<Salle> getAvailableSalles(TypeReunion typeReunion, int nombrePersonnes) {
        List<Salle> salles = salleRepository.findByCapaciteCovidGreaterThanEqual(nombrePersonnes);
        return salles.stream()
                .filter(salle -> salle.getEquipements().containsAll(equipementsRequis(typeReunion)))
                .collect(Collectors.toList());
    }

    private List<Equipement> equipementsRequis(TypeReunion typeReunion) {
        switch (typeReunion) {
            case VC:
                return Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE, Equipement.WEBCAM);
            case SPEC:
                return Arrays.asList(Equipement.TABLEAU);
            case RS:
                return Collections.emptyList();
            case RC:
                return Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE, Equipement.TABLEAU);
            default:
                return Collections.emptyList();
        }
    }
}