package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class MedicalExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "startTime", nullable = false)
    private LocalTime startTime;
    @Column(name = "endTime", nullable = false)
    private LocalTime endTime;
    @Column(name = "statusME", nullable = false) //REQUESTED,APPROVED,ACCEPTED,REJECTED
    private String statusME;
    //REQUESTED - kada pacijent posalje upit adminu za pregledom i soba jos nije dodeljena
    //APPROVED - soba je dodeljena i pacijent treba da prihvati ili odbije zakazani termin
    //ACCEPTED - pacijent je prihvatio termin
    //REJECTED - admin je odbio termin jer je nemoguce naci slobodnu sobu i slobodnog doktora u istom terminu, ili je pacijent odbio odgovor na zahtev
    @Column (name = "done", nullable = false)
    private boolean done;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "discount", nullable = false)
    private double discount;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ExamType examType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room examRoom;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // two way relationship, clinic has a list of all its fast appointments
    @JoinColumn(name="clinic_id", nullable=false)
    private Clinic clinic;
    @Version
    private Long version;

    public MedicalExam() {}
    public MedicalExam(TermDoctor termDoctor){
        this.date = termDoctor.getDateTerm();
        this.startTime = termDoctor.getStartTime();
        this.endTime = termDoctor.getEndTime();
        this.statusME = "REQUESTED";
    }

    public MedicalExam(LocalDate date, LocalTime startTime, LocalTime endTime, String statusME, double price, double discount, ExamType examType, Patient patient, Room examRoom, Doctor doctor, Clinic clinic) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusME = statusME;
        this.price = price;
        this.discount = discount;
        this.examType = examType;
        this.patient = patient;
        this.examRoom = examRoom;
        this.doctor = doctor;
        this.clinic = clinic;
        this.done = false;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getStatusME() {
        return statusME;
    }

    public void setStatusME(String statusME) {
        this.statusME = statusME;
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

    @JsonIgnore
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
