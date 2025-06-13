package edu.fullstack.e3tp25.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fullstack.e3tp25.model.Professeur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ApiProfesseurTests {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init(){

        mvc = webAppContextSetup(context)
                .build();

    }

    @Test
    public void insererNouveauProfesseur_leNomDuRoleDoitEtrePROFESSEUR() throws Exception {

        Professeur professeur = new Professeur();
        professeur.setEmail("professeur@gmail.com");
        professeur.setPassword("root");
        professeur.setAnneesExperience(10);

        String jsonEnvoye = objectMapper.writeValueAsString(professeur);

        MockHttpServletResponse reponseRequeteCreation = mvc.perform(
                post("/api/professeur")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonEnvoye))
            .andReturn().getResponse();

        String jsonRecu = reponseRequeteCreation.getContentAsString();

        Professeur nouveauProfesseur = objectMapper.readValue(jsonRecu, Professeur.class);

        MockHttpServletResponse reponseRequeteVerification = mvc.perform(
                get("/api/professeur/" + nouveauProfesseur.getId()))
                .andReturn().getResponse();

        Professeur professeurCree = objectMapper.readValue(reponseRequeteVerification.getContentAsString(), Professeur.class);

        Assertions.assertEquals("PROFESSEUR", professeurCree.getNomRole());
    }
}
