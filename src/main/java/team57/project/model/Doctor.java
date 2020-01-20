package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {

    @Column(name = "rating", nullable = true, columnDefinition = "double default -1")
    private double rating; //( rating  * reviews + new rating)/(reviews + 1)

    @Column(name = "numberOfReviews", nullable = true, columnDefinition = "bigint default -1")
    private long numberOfReviews; //number of reviews

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "doctors_examTypes", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "examType_id", referencedColumnName = "id"))
    private Set<ExamType> examTypes; //type of medical examination

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "doctors_surgeries", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "surgery_id", referencedColumnName = "id"))
    private Set<Surgery> surgeries;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "doctors_surgeryTypes", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "surgeryType_id", referencedColumnName = "id"))
    private Set<SurgeryType> surgeryTypes;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FastAppointment> fastAppointments;

    @Column(name="workingHoursStart", nullable = false, columnDefinition = "time default '06:00:00'")
    private LocalTime workingHoursStart; // this will be converted into localTime; format 00:00 - 23:59

    @Column(name="workingHoursEnd", nullable = false, columnDefinition = "time default '14:00:00'")
    private LocalTime workingHoursEnd ; // this will be converted into localTime; format 00:00 - 23:59

    @Column(name="removed", nullable = false, columnDefinition = "bit(1) default 0")
    private boolean removed;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Absence> absences;


    public Doctor(){
        super();
    }

    public Doctor(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }

    public Doctor(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber, LocalTime workingHoursStart, LocalTime workingHoursEnd) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.removed = false;
        this.examTypes = new HashSet<ExamType>();
        this.surgeryTypes = new HashSet<SurgeryType>();
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

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    @JsonIgnore
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @JsonIgnore
    public Set<ExamType> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(Set<ExamType> examTypes) {
        this.examTypes = examTypes;
    }

    public Set<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(Set<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    @JsonIgnore
    public Set<SurgeryType> getSurgeryTypes() {
        return surgeryTypes;
    }

    public void setSurgeryTypes(Set<SurgeryType> surgeryTypes) {
        this.surgeryTypes = surgeryTypes;
    }

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

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    @JsonIgnore
    public Set<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }




}
