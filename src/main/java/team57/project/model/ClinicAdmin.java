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
}
