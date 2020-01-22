package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
@Entity
public class MedicalExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "startTime", nullable = false)
    private Time startTime;
    @Column(name = "endTime", nullable = false)
    private Time endTime;
    @Column(name = "reserved", nullable = false)
    private Boolean reserved;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ExamType examType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room examRoom;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

    public MedicalExam() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }
    @JsonIgnore
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Room getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(Room examRoom) {
        this.examRoom = examRoom;
    }
    @JsonIgnore
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
