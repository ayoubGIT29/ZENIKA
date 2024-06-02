package org.xproce.testgpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.testgpt.entities.Salle;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByCapaciteCovidGreaterThanEqual(int capacite);
}
