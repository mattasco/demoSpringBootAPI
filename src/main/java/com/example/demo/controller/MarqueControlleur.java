package com.example.demo.controller;

import com.example.demo.dao.MarqueDao;
import com.example.demo.model.Marque;
import com.example.demo.view.VueMarque;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class MarqueControlleur {

    //------------------------ Injection de dépendances -------------------

    private MarqueDao marqueDao;

    @Autowired
    public MarqueControlleur(MarqueDao marqueDao) {
        this.marqueDao = marqueDao;
    }

    //------------------------ Injection de dépendances ------------------

    @GetMapping("/admin/liste-marque")
    @JsonView(VueMarque.class)
    public List<Marque> listeMarque () {

        return this.marqueDao.findAll();
    }

    @GetMapping("/marque/{id}")
    @JsonView(VueMarque.class)
    public Marque marque(@PathVariable Integer id) {

        return this.marqueDao.findById(id).orElse(null);
    }
    @PostMapping("/admin/marque")
    public ResponseEntity<Marque> createMarque(@RequestBody Marque marque) {

        this.marqueDao.saveAndFlush(marque);
        return ResponseEntity.status(HttpStatus.CREATED).body(marque);
    }

    @DeleteMapping("/admin/marque/{id}")
    public ResponseEntity<Marque> deleteMarque(@PathVariable int id) {
        Optional<Marque> marqueASupprimer = marqueDao.findById(id);
        if(marqueASupprimer.isPresent()){
            this.marqueDao.deleteById(id);
            return ResponseEntity.ok(marqueASupprimer.get());
        }else{
            return ResponseEntity.noContent().build();
        }

    }
}
