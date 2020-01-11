package team57.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Entity
@Getter
@Setter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "organDonor", nullable = true)
    private boolean organDonor;
    @Column(name = "height", nullable = true)
    private double height;
    @Column(name = "weight", nullable = true)
    private double weight;
    @Column(name = "diopter", nullable = true)
    private double diopter;
    @Column(name = "bloodType", nullable = true)
    private String bloodType;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medical_record_allergic_medication", joinColumns = @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"))
    private Set<Medication> allergicToMedications;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medical_record_chronic_condition", joinColumns = @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    private Set<Diagnose> chronicConditions; //codes of diagnoses
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalReport> medicalReports; //obje


}
