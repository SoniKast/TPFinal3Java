package edu.fullstack.e3tp25.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fullstack.e3tp25.model.*;
import edu.fullstack.e3tp25.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class CoursControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private ProfesseurDao professeurDao;
    @Autowired private EtudiantDao etudiantDao;
    @Autowired private SalleDao salleDao;

    @BeforeEach
    public void init() {
        mvc = webAppContextSetup(context)
                .build();
    }

    @Test
    void testAjoutCoursPresentielOK() throws Exception {
        Presentiel cours = new Presentiel();

        Professeur professeur = new Professeur();
        professeur.setEmail("professeur@gmail.com");
        professeur.setPassword("root");
        professeur.setAnneesExperience(10);
        professeur = professeurDao.save(professeur);

        Etudiant etudiant = new Etudiant();
        etudiant.setEmail("etudiant@gmail.com");
        etudiant.setPassword("root");
        etudiant.setDateNaissance(LocalDate.parse("2004-11-18"));
        etudiant = etudiantDao.save(etudiant);

        Salle salle = new Salle();
        salle.setNom("Salle A");
        salle.setCapacite(10);
        salle = salleDao.save(salle);

        cours.setNom("Maths");
        cours.setDebut(LocalDateTime.now().plusDays(1));
        cours.setFin(LocalDateTime.now().plusDays(1).plusHours(8));
        cours.setProfesseur(professeur);
        cours.setEtudiantList(List.of(etudiant));
        cours.setSalle(salle);

        mvc.perform(post("/api/cours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cours)))
                .andExpect(status().isCreated());
    }

    @Test
    void testConflitHoraireProfesseur() throws Exception {
        // Premier cours
        Presentiel cours1 = new Presentiel();

        Professeur professeur = new Professeur();
        professeur.setEmail("professeur2@gmail.com");
        professeur.setPassword("root");
        professeur.setAnneesExperience(10);
        professeur = professeurDao.save(professeur);

        Etudiant etudiant = new Etudiant();
        etudiant.setEmail("etudiant2@gmail.com");
        etudiant.setPassword("root");
        etudiant.setDateNaissance(LocalDate.parse("2004-11-18"));
        etudiant = etudiantDao.save(etudiant);

        Salle salle = new Salle();
        salle.setNom("Salle B");
        salle.setCapacite(10);
        salle = salleDao.save(salle);

        cours1.setNom("Java");
        cours1.setDebut(LocalDateTime.of(2025, 6, 20, 10, 0));
        cours1.setFin(LocalDateTime.of(2025, 6, 20, 15, 0));
        cours1.setProfesseur(professeur);
        cours1.setEtudiantList(List.of(etudiant));
        cours1.setSalle(salle);
        mvc.perform(post("/api/cours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cours1)))
                .andExpect(status().isCreated());

        // Deuxième cours en conflit (même prof, chevauchement)
        Presentiel cours2 = new Presentiel();

        cours2.setNom("Python");
        cours2.setDebut(LocalDateTime.of(2025, 6, 20, 10, 30)); // chevauche
        cours2.setFin(LocalDateTime.of(2025, 6, 20, 15, 0));
        cours2.setProfesseur(professeur);
        cours2.setEtudiantList(List.of(etudiant));
        cours2.setSalle(salle);

        mvc.perform(post("/api/cours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cours2)))
                .andExpect(status().isBadRequest());
    }
}
