package team57.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOrganDonor() {
        return organDonor;
    }

    public void setOrganDonor(boolean organDonor) {
        this.organDonor = organDonor;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDiopter() {
        return diopter;
    }

    public void setDiopter(double diopter) {
        this.diopter = diopter;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Set<Medication> getAllergicToMedications() {
        return allergicToMedications;
    }

    public void setAllergicToMedications(Set<Medication> allergicToMedications) {
        this.allergicToMedications = allergicToMedications;
    }

    public Set<Diagnose> getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(Set<Diagnose> chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public Set<MedicalReport> getMedicalReports() {
        return medicalReports;
    }

    public void setMedicalReports(Set<MedicalReport> medicalReports) {
        this.medicalReports = medicalReports;
    }
}
