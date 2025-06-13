package edu.fullstack.e3tp25.dao;

import edu.fullstack.e3tp25.model.Cours;
import edu.fullstack.e3tp25.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursDao extends JpaRepository<Cours, Integer> {
}
