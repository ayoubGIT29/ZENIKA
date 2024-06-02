package org.xproce.ZENIKA.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xproce.ZENIKA.dto.ReservationRequest;
import org.xproce.ZENIKA.metier.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> reserveSalle(@RequestBody ReservationRequest reservationRequest) {
        reservationService.reserveSalle(
                reservationRequest.getDate(),
                reservationRequest.getDebut(),
                reservationRequest.getFin(),
                reservationRequest.getTypeReunion(),
                reservationRequest.getNombrePersonnes()
        );
        return ResponseEntity.ok("Réservation réussie");
    }
}
