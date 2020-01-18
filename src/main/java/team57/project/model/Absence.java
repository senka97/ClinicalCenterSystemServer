package team57.project.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="typeOfAbsence", nullable=false) //Paid vacation, Unpaid leave, Sick leave
    private String typeOfAbsence;
    @Column(name="statusOfAbsence", nullable=false)
    private String statusOfAbsence; //REQUESTED, APPROVED, REJECTED
    @Column(name="startDate", nullable=false)
    private LocalDate startDate;
    @Column(name="endDate", nullable=false)
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=true)
    private Doctor doctor; //if it's nurse it will be null
    @ManyToOne
    @JoinColumn(name="nurse_id", nullable=true)
    private Nurse nurse;


    public Absence(){
    }

    public Absence(String typeOfAbsence, String statusOfAbsence, LocalDate startDate, LocalDate endDate) {
        this.typeOfAbsence = typeOfAbsence;
        this.statusOfAbsence = statusOfAbsence;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTypeOfAbsence() {
        return typeOfAbsence;
    }

    public void setTypeOfAbsence(String typeOfAbsence) {
        this.typeOfAbsence = typeOfAbsence;
    }

    public String getStatusOfAbsence() {
        return statusOfAbsence;
    }

    public void setStatusOfAbsence(String statusOfAbsence) {
        this.statusOfAbsence = statusOfAbsence;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }
}
