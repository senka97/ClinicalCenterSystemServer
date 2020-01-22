package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "verified", nullable = false)
    private boolean verified;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //bio je set, sad je promenjeno
    private Medication medication;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //dodato
    private Patient patient;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //dodato
    private Doctor doctor;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Nurse nurse;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;

    public Prescription() {
    }

    public Prescription(boolean verified, Medication medication, Nurse nurse) {
        this.verified = verified;
        this.medication = medication;
        this.nurse = nurse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

}