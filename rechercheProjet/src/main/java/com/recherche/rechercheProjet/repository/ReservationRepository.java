package com.recherche.rechercheProjet.repository;


import com.recherche.rechercheProjet.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
