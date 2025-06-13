package edu.fullstack.e3tp25.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CoursDTO {

    public String nom;
    public LocalDateTime debut;
    public LocalDateTime fin;

    public Integer professeurId;
    public List<Integer> etudiantIds;
    public Integer salleId;
}

