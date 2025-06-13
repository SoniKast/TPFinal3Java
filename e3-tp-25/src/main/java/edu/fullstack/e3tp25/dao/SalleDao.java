package edu.fullstack.e3tp25.dao;

import edu.fullstack.e3tp25.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleDao extends JpaRepository<Salle, Integer> {

}
