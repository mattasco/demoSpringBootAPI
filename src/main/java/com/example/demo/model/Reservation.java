package com.example.demo.model;

import com.example.demo.view.VueMateriel;
import com.example.demo.view.VueReservation;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CleReservation.class)
@Data
public class Reservation {

    @Id
    private Integer emprunteurId;

    @Id
    private Integer materielId;

    @Id
    @JsonView(VueReservation.class)
    private Date date;

    @ManyToOne
    @MapsId("emprunteur_id")
    @JsonView(VueReservation.class)
    private Utilisateur emprunteur;

    @ManyToOne
    @MapsId("materiel_id")
    @JsonView(VueReservation.class)
    private Materiel materiel;

}
