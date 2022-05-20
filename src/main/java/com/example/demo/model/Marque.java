package com.example.demo.model;

import com.example.demo.view.VueMarque;
import com.example.demo.view.VueMateriel;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueMarque.class, VueMateriel.class})
    private  Integer id;

    @JsonView({VueMarque.class, VueMateriel.class})
    private String nom;

    @OneToMany(mappedBy = "marque")                             // permet de faire la liaison dans les 2 sens
    @JsonView(VueMarque.class)
    private List<Materiel> listeMateriel = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Materiel> getListeMateriel() {
        return listeMateriel;
    }

    public void setListeMateriel(List<Materiel> listeMateriel) {
        this.listeMateriel = listeMateriel;
    }
}
