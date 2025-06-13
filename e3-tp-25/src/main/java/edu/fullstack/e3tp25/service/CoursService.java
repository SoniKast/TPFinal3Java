package edu.fullstack.e3tp25.service;

import edu.fullstack.e3tp25.dao.CoursDao;
import edu.fullstack.e3tp25.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CoursService {

    private final CoursDao coursDao;

    @Autowired
    public CoursService(CoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public Cours creerCours(Cours cours) throws IllegalArgumentException {
        LocalDateTime debut = cours.getDebut();
        LocalDateTime fin = cours.getFin();

        // Vérification disponibilité professeur
        if (coursDao.existsByProfesseurAndHoraire(cours.getProfesseur(), debut, fin)) {
            throw new IllegalArgumentException("Le professeur a déjà un cours à cette période.");
        }

        // Vérification disponibilité étudiants
        for (Etudiant e : cours.getEtudiantList()) {
            if (coursDao.existsByEtudiantAndHoraire(e, debut, fin)) {
                throw new IllegalArgumentException("Un étudiant a déjà un cours à cette période.");
            }
        }

        // Si c’est un cours en présentiel, on vérifie salle et capacité
        if (cours instanceof Presentiel presentiel) {
            Salle salle = presentiel.getSalle();

            if (coursDao.existsBySalleAndHoraire(salle, debut, fin)) {
                throw new IllegalArgumentException("La salle est déjà occupée.");
            }

            if (presentiel.getEtudiantList().size() > salle.getCapacite()) {
                throw new IllegalArgumentException("Le nombre d'étudiants dépasse la capacité de la salle.");
            }
        }

        return coursDao.save(cours);
    }
}
