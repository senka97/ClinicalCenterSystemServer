package team57.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Entity
public class Surgery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "startTime", nullable = false)
    private Time startTime;
    @Column(name = "endTime", nullable = false)
    private Time endTime;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurgeryType surgeryType;
    @ManyToMany(mappedBy = "surgeries")
    private Set<Doctor> doctors;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;

    public Surgery(){}

    public Surgery(Date date, Time startTime, Time endTime, SurgeryType surgeryType, Set<Doctor> doctors, Patient patient) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.surgeryType = surgeryType;
        this.doctors = doctors;
        this.patient = patient;
    }

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

    public SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
