package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Role;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.UserDetailsDemo;
import com.example.demo.security.UserDetailsServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class UtilisateurControlleur {

    //------------------------ Injection de dépendances -------------------

    private UtilisateurDao utilisateurDao;

    @Autowired   // injection de dépendances ( classe qui dépendent d'autre classe )
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceDemo userDetailServiceDemo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UtilisateurControlleur(UtilisateurDao utilisateurDao) {

        this.utilisateurDao = utilisateurDao;
    }

    //------------------------ Injection de dépendances ------------------

    @PostMapping("/inscription")
    public String inscription(@RequestBody Utilisateur utilisateur) throws Exception {

        utilisateur.setMotDePasse(encoder.encode(utilisateur.getMotDePasse()));
        Role roleUser = new Role();
        roleUser.setId(1);
        utilisateur.getListeRole().add(roleUser);

        utilisateurDao.save(utilisateur);

        return "Utilisateur crée";
    }

    @PostMapping("/connexion")            // renvoyer un token si l'utilisateur existe dans la BDD
    public Map<String, String> connexion(@RequestBody Utilisateur utilisateur) throws Exception {

        //Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findByNom(utilisateur.getNom());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getNom(),
                            utilisateur.getMotDePasse()
                    )
            );
        }catch (BadCredentialsException e) {
            Map<String,String> retour=new HashMap<>();
            retour.put("erreur","mauvais log mdp");
            return retour;
        }

        UserDetailsDemo userDetails = userDetailServiceDemo.loadUserByUsername(utilisateur.getNom());

        Map<String,String> retour= new HashMap<>();
        retour.put("token",jwtUtils.generateToken(userDetails));


        return retour;
    }

    @GetMapping("/liste-utilisateur")
    public List<Utilisateur> listeUtilisateur() {

        return this.utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur-par-nom/{nom}")
    public Utilisateur utilisateurParNom(@PathVariable String nom) {

            return this.utilisateurDao.findByNom(nom).orElse(null);
    }


    @GetMapping("/utilisateur/{id}")
    public Utilisateur utilisateur(@PathVariable Integer id) {

        return this.utilisateurDao.findById(id).orElse(null);
    }

    @PostMapping("/utilisateur")
    public String createUtilisateur(@RequestBody Utilisateur utilisateur) {

        this.utilisateurDao.save(utilisateur);

        return "ok";
    }

    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> deleteUtilisateur(@PathVariable int id){

        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);

        if(utilisateurAsupprimer.isPresent()){
            this.utilisateurDao.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
