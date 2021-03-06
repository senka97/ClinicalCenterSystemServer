package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "numberOfReviews", nullable = false)
    private long numberOfReviews;
    @OneToMany(mappedBy = "clinic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClinicAdmin> clinicAdmins;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clinic_patients", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    private Set<Patient> patients;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Doctor> doctors;
    @OneToMany(mappedBy = "clinic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Nurse> nurses;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> rooms;
    @ManyToMany(mappedBy = "clinics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SurgeryType> surgeryTypes;
    @ManyToMany(mappedBy = "clinics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamType> examTypes;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FastAppointment> fastAppointments;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Surgery> surgeries;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Absence> absences;


    public Clinic() {}


    public Clinic(Long id, String name, String address, String description, double rating, long numberOfReviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;

        this.doctors = new HashSet<>();
        this.examTypes = new HashSet<>();
        this.absences = new HashSet<>();
        this.clinicAdmins = new HashSet<>();
        this.patients = new HashSet<>();
        this.nurses = new HashSet<>();
        this.medicalExams = new HashSet<>();

    }

    public Clinic(String name, String address, String description){
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = 0;
        this.numberOfReviews = 0;
        this.clinicAdmins = new HashSet<>();
        this.patients = new HashSet<>();
        this.doctors = new HashSet<>();
        this.nurses = new HashSet<>();
        this.rooms = new HashSet<>();
        this.surgeryTypes = new HashSet<>();
        this.examTypes = new HashSet<>();
        this.medicalExams = new HashSet<>();
        this.fastAppointments = new HashSet<>();
        this.surgeries = new HashSet<>();
        this.absences = new HashSet<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(long numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @JsonIgnore
    public Set<ClinicAdmin> getClinicAdmins() {
        return clinicAdmins;
    }

    public void setClinicAdmins(Set<ClinicAdmin> clinicAdmins) {
        this.clinicAdmins = clinicAdmins;
    }
    @JsonIgnore
    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
    @JsonIgnore
    public Set<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(Set<Nurse> nurses) {
        this.nurses = nurses;
    }

    public Set<SurgeryType> getSurgeryTypes() {
        return surgeryTypes;
    }

    public void setSurgeryTypes(Set<SurgeryType> surgeryTypes) {
        this.surgeryTypes = surgeryTypes;
    }

    public Set<ExamType> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(Set<ExamType> examTypes) {
        this.examTypes = examTypes;
    }

    public Set<MedicalExam> getMedicalExams() {
        return medicalExams;
    }

    public void setMedicalExams(Set<MedicalExam> medicalExams) {
        this.medicalExams = medicalExams;
    }

    public Set<FastAppointment> getFastAppointments() {
        return fastAppointments;
    }

    public void setFastAppointments(Set<FastAppointment> fastAppointments) {
        this.fastAppointments = fastAppointments;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Set<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(Set<Surgery> surgeries) {
        this.surgeries = surgeries;
    }
}
