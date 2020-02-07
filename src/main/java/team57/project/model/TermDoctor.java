package team57.project.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TermDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="dateTerm",nullable = false)
    private LocalDate dateTerm;
    @Column(name="startTime", nullable = false)
    private LocalTime startTime;
    @Column(name="endTime", nullable = false)
    private LocalTime endTime;
    @Column(name="free", nullable = false)
    private boolean free;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    public TermDoctor(){

    }

    public TermDoctor(LocalDate dateTerm, LocalTime startTime, LocalTime endTime, boolean free, Doctor doctor){
        this.dateTerm = dateTerm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.free = free;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTerm() {
        return dateTerm;
    }

    public void setDateTerm(LocalDate dateTerm) {
        this.dateTerm = dateTerm;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
