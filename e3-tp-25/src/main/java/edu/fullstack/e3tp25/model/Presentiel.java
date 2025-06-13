package edu.fullstack.e3tp25.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PRESENTIEL")
public class Presentiel extends Cours {
    @ManyToOne
    private Salle salle;
}
