package com.example.demo.model;

import com.example.demo.view.VueReservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ListIndexBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// ORM
// Object Relationnal Mapping ( Liaison entre la table et l'objet )

@Entity
@EntityListeners(AuditingEntityListener.class)// permet de transformer la classe en table dans la BDD( Génération auto dans la BDD)
@Data
public class Utilisateur {

    @Id                                                    // l'annotation signifie que ID est la clef primaire dans la BDD
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // Id auto générer par la BDD
    @JsonView(VueReservation.class)
    private Integer id;                          // Integer puisque c'est un objet il peut contenir la valeur Null contrairement à int

    @JsonView(VueReservation.class)
    private String nom;

    @JsonView(VueReservation.class)
    private String prenom;

    //@JsonIgnore
    private String motDePasse;

//    @JsonView(VueReservation.class)
//    private boolean admin;                  // permet de donner un role admin

    @ManyToMany
    @JoinTable(
            name="role_utilisateur",
            joinColumns = @JoinColumn(name="utilisateur_id"),      // permet de changer le nom d'un champs ds la BDD
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> listeRole = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
}
