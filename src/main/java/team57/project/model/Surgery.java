package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import team57.project.model.Room;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Entity
public class Surgery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "startTime", nullable = true)
    private LocalTime startTime;
    @Column(name = "endTime", nullable = true)
    private LocalTime endTime;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "discount", nullable = false)
    private double discount;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room surgeryRoom;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurgeryType surgeryType;
    @Column(name = "statusS", nullable = false) //REQUESTED,APPROVED,ACCEPTED,REJECTED
    private String statusS;
    //REQUESTED - kada doktor posalje upit za operaciju adminu i soba jos nije dodeljena
    //APPROVED - soba je dodeljena
    //REJECTED - admin je odbio termin jer je nemoguce naci slobodnu sobu i slobodnog doktora u istom terminu
    @ManyToMany(mappedBy = "surgeries")
    private Set<Doctor> doctors;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Patient patient;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // two way relationship, clinic has a list of all its fast appointments
    @JoinColumn(name="clinic_id", nullable=false)
    private Clinic clinic;

    @Version
    private Long version;

    public Surgery(){}

    public Surgery(LocalDate date, LocalTime startTime, LocalTime endTime, SurgeryType surgeryType, Room surgeryRoom, Set<Doctor> doctors, Patient patient) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.surgeryType = surgeryType;
        this.surgeryRoom = surgeryRoom;
        this.doctors = doctors;
        this.patient = patient;
        this.statusS = "REQUESTED";
    }

    public String getStatusS() {
        return statusS;
    }

    public void setStatusS(String statusS) {
        this.statusS = statusS;
    }

    public Room getSurgeryRoom() {
        return surgeryRoom;
    }

    public void setSurgeryRoom(Room surgeryRoom) {
        this.surgeryRoom = surgeryRoom;
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
