package edu.fullstack.e3tp25.dao;

import edu.fullstack.e3tp25.model.Cours;
import edu.fullstack.e3tp25.model.Etudiant;
import edu.fullstack.e3tp25.model.Professeur;
import edu.fullstack.e3tp25.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CoursDao extends JpaRepository<Cours, Integer> {

    @Query("SELECT COUNT(c) > 0 FROM Cours c WHERE c.professeur = :prof AND ((:debut BETWEEN c.debut AND c.fin) OR (:fin BETWEEN c.debut AND c.fin))")
    boolean existsByProfesseurAndHoraire(@Param("prof") Professeur prof, @Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(c) > 0 FROM Cours c JOIN c.etudiantList e WHERE e = :etudiant AND ((:debut BETWEEN c.debut AND c.fin) OR (:fin BETWEEN c.debut AND c.fin))")
    boolean existsByEtudiantAndHoraire(@Param("etudiant") Etudiant etudiant, @Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(c) > 0 FROM Cours c WHERE c.salle = :salle AND ((:debut BETWEEN c.debut AND c.fin) OR (:fin BETWEEN c.debut AND c.fin))")
    boolean existsBySalleAndHoraire(@Param("salle") Salle salle, @Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

}
