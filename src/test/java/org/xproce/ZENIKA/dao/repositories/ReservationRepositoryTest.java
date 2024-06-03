package org.xproce.ZENIKA.dao.repositories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xproce.ZENIKA.dao.entities.Reservation;
import org.xproce.ZENIKA.dao.entities.Salle;
import org.xproce.ZENIKA.dao.repositories.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Test
    public void testFindByDateAndSalle() {
        // Create and save a Salle object
        Salle salle = new Salle();
        // Set properties if necessary
        salleRepository.save(salle);

        // Create and save a Reservation object
        Reservation reservation = new Reservation();
        reservation.setDate(LocalDate.of(2024, 6, 3));
        reservation.setSalle(salle); // Set the salle for the reservation
        // Set other properties if necessary
        reservationRepository.save(reservation);

        // Perform the query
        List<Reservation> reservations = reservationRepository.findByDateAndSalle(LocalDate.of(2024, 6, 3), salle);

        // Verify that the returned list contains the saved reservation
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

}
