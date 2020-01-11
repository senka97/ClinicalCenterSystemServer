package team57.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
@Entity
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
