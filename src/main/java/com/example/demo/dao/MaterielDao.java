package com.example.demo.dao;

import com.example.demo.model.Materiel;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository             // permet d'injecter l'interface JPA repository
public interface MaterielDao extends JpaRepository<Materiel, Integer> {

}

//