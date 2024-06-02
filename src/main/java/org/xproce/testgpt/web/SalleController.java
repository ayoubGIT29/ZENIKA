package org.xproce.testgpt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xproce.testgpt.entities.Salle;
import org.xproce.testgpt.entities.TypeReunion;
import org.xproce.testgpt.metier.*;

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

