package com.example.demo.dao;

import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository             // permet d'injecter l'interface JPA repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur > findByNom(String nom);

    @Query("FROM Utilisateur u JOIN FETCH u.listeRole WHERE u.nom = :nom")           // requete HQL ( Hibernate ) :nom est lier avec @Param("nom")
    Optional<Utilisateur > findByNomWithRoles(@Param("nom") String nom);
}

//