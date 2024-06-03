package org.xproce.ZENIKA.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.ZENIKA.dao.entities.Salle;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByCapaciteCovidGreaterThanEqual(int capacite);
    List<Salle> findByNom(String nom);
}
