package com.example.demo.model;

import com.example.demo.view.VueMarque;
import com.example.demo.view.VueMateriel;
import com.example.demo.view.VueReservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Materiel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView( {VueMarque.class, VueMateriel.class, VueReservation.class} )     // informations placer dans un tableau
    private  Integer id;

    @Column(unique = true)
    @JsonView( {VueMarque.class, VueMateriel.class, VueReservation.class} )
    private String code;

    @JsonView( {VueMarque.class, VueMateriel.class, VueReservation.class} )
    private String nom;

    @ManyToOne                              // représente une clef étrangère dans la BDD ( via le schéma UML : relation Many to One )
    //@JoinColumn(name = "id_marque")         // permet de changer le titre de la clef étrangère qui est mise par défaut
    @JsonView(VueMateriel.class)
    private Marque marque;

    @ManyToMany
    @JsonView(VueMateriel.class)
    @JoinTable(
            name = "materiel_specificite",
            joinColumns = @JoinColumn(name = "materiel_id"),    // s'il y a une erreur supprimer à la main la colonne dans la BDD avant de relancer
            inverseJoinColumns = @JoinColumn(name = "specificite_id"))      // ici on renomme les noms des colonnes dans la BDD
    private List<Specificite> listeSpecificite = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public List<Specificite> getListeSpecificite() {
        return listeSpecificite;
    }

    public void setListeSpecificite(List<Specificite> listeSpecificite) {
        this.listeSpecificite = listeSpecificite;
    }


}
