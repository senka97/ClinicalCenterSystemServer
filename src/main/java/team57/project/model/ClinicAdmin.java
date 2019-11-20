package team57.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CLINIC_ADMIN")
public class ClinicAdmin extends User{

    public ClinicAdmin() {
        super();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Clinic clinic;

    public ClinicAdmin(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber, Clinic clinic) {
        super(name,surname,email,password,address,city,country,phoneNumber,serialNumber);
        this.clinic = clinic;
    }

    public ClinicAdmin()
    {

    }
}
