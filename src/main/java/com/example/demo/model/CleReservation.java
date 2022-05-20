package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable                    // permet de dire que ça va être utilisable par une autre classe (ici dans dao Reservation)
public class CleReservation implements Serializable {    // permet de dire que cette classe peut etre ecrite sous forme de texte

    @Column(name = "emprunteur_id")      //permet de renommer le nom de la colomne
    private Integer emprunteurId;

    @Column(name = "materiel_id")
    private Integer materielId;

    @Column(name = "date")
    private Date date;

    public Integer getEmprunteurId() {
        return emprunteurId;
    }

    public void setEmprunteurId(Integer emprunteurId) {
        this.emprunteurId = emprunteurId;
    }

    public Integer getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Integer materielId) {
        this.materielId = materielId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
