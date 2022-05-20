package com.example.demo.dao;

import com.example.demo.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository             // permet d'injecter l'interface JPA repository
public interface MarqueDao extends JpaRepository<Marque, Integer> {

}

//