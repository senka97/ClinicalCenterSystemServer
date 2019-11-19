package team57.project.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends User {

    public Nurse(){
        super();
    }
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;
}
