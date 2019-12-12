package team57.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
@Entity
@Getter
@Setter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "organDonor", nullable = false)
    private boolean organDonor;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "diopter", nullable = false)
    private double diopter;
    @Column(name = "bloodType", nullable = false)
    private String bloodType;
//    @OneToOne(mappedBy = "medicalRecord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Patient Patient;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Medication> allergicToMedications;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Diagnose> chronicConditions; //codes of diagnoses
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalReport> medicalReports; //object


}
