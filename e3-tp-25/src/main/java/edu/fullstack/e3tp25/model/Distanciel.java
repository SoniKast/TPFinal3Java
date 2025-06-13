package edu.fullstack.e3tp25.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("DISTANCIEL")
public class Distanciel extends Cours {

    protected String lien_reunion;
}
