package com.example.demo.dao;

import com.example.demo.model.CleReservation;
import com.example.demo.model.Materiel;
import com.example.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository             // permet d'injecter l'interface JPA repository
public interface ReservationDao extends JpaRepository<Reservation, CleReservation> {

    Optional<Reservation> findByEmprunteurIdAndMaterielIdAndDate(
            Integer emprunteurId,
            Integer materielId,
            Date date
    );

    void deleteByEmprunteurIdAndMaterielIdAndDate(
            Integer emprunteurId,
            Integer matrielId,
            Date date
    );

}

