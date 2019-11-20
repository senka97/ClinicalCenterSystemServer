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

    public Nurse(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }
}
