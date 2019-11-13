package team57.project.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLINICAL_CENTER_ADMIN")
public class ClinicalCenterAdmin extends User{

}
