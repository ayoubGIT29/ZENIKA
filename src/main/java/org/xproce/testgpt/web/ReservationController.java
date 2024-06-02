package org.xproce.testgpt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xproce.testgpt.dto.ReservationRequest;
import org.xproce.testgpt.metier.ReservationService;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public String reserveSalle(@RequestBody ReservationRequest reservationRequest) {try{
        reservationService.reserveSalle(
                reservationRequest.getDate(),
                reservationRequest.getDebut(),
                reservationRequest.getFin(),
                reservationRequest.getTypeReunion(),
                reservationRequest.getNombrePersonnes()
        );
        return "Réservation réussie";

    }
    catch (Exception e){
        return e.getMessage();
    }
//        if (success) {
//            return ResponseEntity.ok("Réservation réussie");
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Salle non disponible");
//        }
    }
}
