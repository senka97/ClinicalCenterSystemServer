package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;
@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

    public Patient(){
        super();
    }
    @Column(name = "activatedAccount", nullable = true)
    private String activatedAccount; //ACCEPTED, REJECTED, UNRESOLVED
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="medicalRecord_id")
    private MedicalRecord medicalRecord;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Surgery> surgeries;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FastAppointment> fastAppointments;
    @ManyToMany(mappedBy = "patients",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Clinic> allClinics;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Doctor> ratedDoctors;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Clinic> ratedClinics;

    public Patient(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
    @JsonIgnore
    public Set<Clinic> getAllClinics() {
        return allClinics;
    }

    public void setAllClinics(Set<Clinic> allClinics) {
        this.allClinics = allClinics;
    }

    @JsonIgnore
    public Set<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(Set<Surgery> surgeries) {
        this.surgeries = surgeries;
    }
    @JsonIgnore
    public Set<MedicalExam> getMedicalExams() {
        return medicalExams;
    }

    public void setMedicalExams(Set<MedicalExam> medicalExams) {
        this.medicalExams = medicalExams;
    }

    @JsonIgnore
    public Set<FastAppointment> getFastAppointments() {
        return fastAppointments;
    }

    public void setFastAppointments(Set<FastAppointment> fastAppointments) {
        this.fastAppointments = fastAppointments;
    }

    public String getActivatedAccount() {
        return activatedAccount;
    }

    public void setActivatedAccount(String activatedAccount) {
        this.activatedAccount = activatedAccount;
    }

    @JsonIgnore
    public Set<Doctor> getDoctors() {
        return ratedDoctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.ratedDoctors = doctors;
    }

    @JsonIgnore
    public Set<Clinic> getClinics() {
        return ratedClinics;
    }

    public void setClinics(Set<Clinic> clinics) {
        this.ratedClinics = clinics;
    }

}
