package org.xproce.ZENIKA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.ZENIKA.entities.Salle;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByCapaciteCovidGreaterThanEqual(int capacite);
}
