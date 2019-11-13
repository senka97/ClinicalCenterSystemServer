package team57.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Builder
@Setter
@Getter
@ToString

@Entity
@DiscriminatorValue("CLINIC_ADMIN")
public class ClinicAdmin extends User{
    @ManyToOne(fetch = FetchType.LAZY)
    private Clinic clinic;
}
