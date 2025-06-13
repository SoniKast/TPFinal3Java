package edu.fullstack.e3tp25.service;

import edu.fullstack.e3tp25.dao.CoursDao;
import edu.fullstack.e3tp25.dao.SalleDao;
import edu.fullstack.e3tp25.model.Cours;
import edu.fullstack.e3tp25.model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursService {

    @Autowired
    private CoursDao coursDao;

    @Autowired
    private SalleDao salleDao;

    public Cours ajouterCours(Cours cours) {
        // Vérification conflits professeurs
        boolean conflitProf = coursDao.existsByProfesseurAndHoraire(cours.getProfesseur(), cours.getDebut(), cours.getFin());
        if (conflitProf) throw new RuntimeException("Le professeur a déjà un cours à cette période");

        // Vérification conflits étudiants
        for (Etudiant etudiant : cours.getEtudiantList()) {
            boolean conflit = coursDao.existsByEtudiantAndHoraire(etudiant, cours.getDebut(), cours.getFin());
            if (conflit) throw new RuntimeException("L'étudiant " + etudiant.getId() + " a déjà un cours");
        }

        // Si présentiel
        if (cours.getSalle() != null) {
            boolean salleOccupee = coursDao.existsBySalleAndHoraire(cours.getSalle(), cours.getDebut(), cours.getFin());
            if (salleOccupee) throw new RuntimeException("Salle occupée");

            if (cours.getEtudiantList().size() > cours.getSalle().getCapacite())
                throw new RuntimeException("Trop d'étudiants pour cette salle");
        }

        return coursDao.save(cours);
    }
}
