package team57.project.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLINICAL_CENTER_ADMIN")
public class ClinicalCenterAdmin extends User{

    public ClinicalCenterAdmin(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }

    public ClinicalCenterAdmin() {
        super();

    }

}
