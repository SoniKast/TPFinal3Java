package edu.fullstack.e3tp25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String nom;

    private int capacite;

    @OneToMany(mappedBy = "salle")
    private List<Presentiel> coursPresentiels;
}
