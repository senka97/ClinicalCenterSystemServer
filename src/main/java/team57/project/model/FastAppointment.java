package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class FastAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="dateFA", nullable = false)
    private LocalDate dateFA; //dd-MM-yyyy hh:mm:ss , this will be converted from String with localDateTime.parse
    @Column(name="timeFA", nullable = false)
    private LocalTime timeFA;
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
    @Column(name="discount", nullable = false)
    private double discount;
    @Column(name="reserved", nullable = false)
    private boolean reserved;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // two way relationship, clinic has a list of all its fast appointments
    @JoinColumn(name="clinic_id", nullable=false)
    private Clinic clinic; //da bi se kod pacijenta u prikazu istorije pregleda videlo u kojoj je klinici

    /*@Version
    private int version;*/

    public FastAppointment(){

    }

    public FastAppointment(LocalDate dateFA,LocalTime timeFA, int duration, ExamType examType, Room room, Doctor doctor, Patient patient, double price, double discount, boolean reserved, Clinic clinic) {
        this.dateFA = dateFA;
        this.timeFA = timeFA;
        this.duration = duration;
        this.examType = examType;
        this.room = room;
        this.doctor = doctor;
        this.patient = patient;
        this.price = price;
        this.discount = discount;
        this.reserved = reserved;
        this.clinic = clinic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFA() {
        return dateFA;
    }

    public void setDateFA(LocalDate dateFA) {
        this.dateFA = dateFA;
    }

    public LocalTime getTimeFA() {
        return timeFA;
    }

    public void setTimeFA(LocalTime timeFA) {
        this.timeFA = timeFA;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /*public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }*/


    @JsonIgnore
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
