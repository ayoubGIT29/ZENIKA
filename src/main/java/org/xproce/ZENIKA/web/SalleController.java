package org.xproce.ZENIKA.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.entities.TypeReunion;
import org.xproce.ZENIKA.metier.*;

import java.util.List;

@RestController
@RequestMapping("/salles")
public class SalleController {
    @Autowired
    private SalleService salleService;

    @GetMapping
    public List<Salle> getSalles(@RequestParam TypeReunion typeReunion, @RequestParam int nombrePersonnes) {
        return salleService.getAvailableSalles(typeReunion, nombrePersonnes);
    }
}

