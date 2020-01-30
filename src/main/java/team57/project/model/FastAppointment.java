package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FastAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="dateTime", nullable = false)
    private LocalDateTime dateTime; //dd-MM-yyyy hh:mm:ss , this will be converted from String with localDateTime.parse
    @Column(name="duration", nullable = false)
    private int duration; //in minutes
    @ManyToOne // one way relationship
    @JoinColumn(name="examType_id", nullable=false)
    private ExamType examType;
    @ManyToOne // one way relationship
    @JoinColumn(name="room_id", nullable=false)
    private Room room; //only exam room, not surgery room
    @ManyToOne // two way relationship, doctor has a list of all his fast appointments
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;
    @ManyToOne // two way relationship, patient has a list of all his fast appointments
    @JoinColumn(name="patient_id", nullable=true) //at the beginning it is free appointment
    private Patient patient;
    @Column(name="price", nullable = false)
    private double price;
    @Column(name="done", nullable = false)
    private boolean done;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // two way relationship, clinic has a list of all its fast appointments
    @JoinColumn(name="clinic_id", nullable=false)
    private Clinic clinic; //da bi se kod pacijenta u prikazu istorije pregleda videlo u kojoj je klinici

    public FastAppointment(){

    }

    public FastAppointment(LocalDateTime dateTime, int duration, ExamType examType, Room room, Doctor doctor, Patient patient, double price, boolean done, Clinic clinic) {
        this.dateTime = dateTime;
        this.duration = duration;
        this.examType = examType;
        this.room = room;
        this.doctor = doctor;
        this.patient = patient;
        this.price = price;
        this.done = done;
        this.clinic = clinic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @JsonIgnore
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
