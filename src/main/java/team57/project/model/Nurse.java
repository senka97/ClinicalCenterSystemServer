package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends User {


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;
    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Absence> absences;

    public Nurse(){
        super();
    }


    public Nurse(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }

    @JsonIgnore
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @JsonIgnore
    public Set<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }
}
