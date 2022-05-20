package com.example.demo;

import com.example.demo.model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  // permet d'importer toutes les méthodes static d'une classe
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // permet de donner un num de port aléatoire et faire les tests pendant que l'appli tourne
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	private void initialisation() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	void utilisateurListeRole_initialise() {

		request();

		Utilisateur utilisateur = new Utilisateur();

		assertDoesNotThrow(() -> utilisateur.getListeRole().size());

	}

	@Test
	@WithMockUser(username = "doe", roles = {"USER"})
	void simpleUserGetListeMateriel_reponse200ok() throws Exception {

		mvc.perform(get("/liste-materiel")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "doe", roles = {"USER"})
	void simpleUserGetListeMarque_reponse403Forbiden() throws Exception {

		mvc.perform(get("/admin/liste-marque")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void adminGetListeMarque_reponse200ok() throws Exception {

		mvc.perform(get("/admin/liste-marque")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "doe", roles = {"USER"})
	void adminGetListeMaterielId1_materielComporteECRANDELL001() throws Exception {

		mvc.perform(get("/materiel/1"))
				.andExpect(
						jsonPath("$.code").value("ECRANDELL001")     // $ = l'objet qu'on à reçut
				);
	}

	@Test
	@WithMockUser(username = "doe", roles = {"USER"})
	void simpleUserGetListeReservation_utilisateurSansMotDePasse() throws Exception {

		mvc.perform(get("/Liste-reservation"))
				.andExpect(
						jsonPath("$[0].emprunteur.motDePasse").doesNotExist()
				);
	}


}
