package team57.project.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "diopter", nullable = false)
    private double diopter;
    @Column(name = "bloodType", nullable = false)
    private String bloodType;
    @OneToOne(mappedBy = "medicalRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient Patient;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Medication> allergicToMedications;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Diagnose> chronicConditions; //codes of diagnoses
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalReport> medicalReports; //object


}
