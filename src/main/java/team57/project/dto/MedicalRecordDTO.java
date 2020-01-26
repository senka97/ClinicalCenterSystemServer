package team57.project.dto;

import team57.project.model.Diagnose;
import team57.project.model.MedicalReport;
import team57.project.model.Medication;

import java.util.Set;

public class MedicalRecordDTO {

    private Long id;
    private String dateOfBirth;
    private String organDonor;
    private double height;
    private double weight;
    private String diopter;
    private String bloodType;
    private Set<Medication> allergicToMedications;
    private Set<Diagnose> chronicConditions; //codes of diagnoses
    private Set<MedicalReport> medicalReports; //object

    public MedicalRecordDTO(){

    }

    public MedicalRecordDTO(Long id, String dateOfBirth, String organDonor, double height, double weight, String diopter, String bloodType, Set<Medication> allergicToMedications, Set<Diagnose> chronicConditions, Set<MedicalReport> medicalReports) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.organDonor = organDonor;
        this.height = height;
        this.weight = weight;
        this.diopter = diopter;
        this.bloodType = bloodType;
        this.allergicToMedications = allergicToMedications;
        this.chronicConditions = chronicConditions;
        this.medicalReports = medicalReports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOrganDonor() {
        return organDonor;
    }

    public void setOrganDonor(String organDonor) {
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

    public String getDiopter() {
        return diopter;
    }

    public void setDiopter(String diopter) {
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
