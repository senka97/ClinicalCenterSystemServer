package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CLINIC_ADMIN")
@Getter
public class ClinicAdmin extends User{


    @ManyToOne(fetch = FetchType.EAGER)
    private Clinic clinic;

    public ClinicAdmin(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber, Clinic clinic) {
        super(name,surname,email,password,address,city,country,phoneNumber,serialNumber);
        this.clinic = clinic;
    }

    public ClinicAdmin() {
    }

    public ClinicAdmin(Long id, String email, String name) {
        super(id,email,name);

    }

    @JsonIgnore
    public Clinic getClinic() {
        return this.clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

}
