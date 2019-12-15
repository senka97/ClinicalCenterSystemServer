package team57.project.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams; //lista slobodnih termina za preglede
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Doctor> doctors;
    @OneToMany(mappedBy = "clinic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Nurse> nurses;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> rooms;
    @ManyToMany
    @JoinTable(name = "clinics_surgeryTypes", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "surgeryType_id", referencedColumnName = "id"))
    private Set<SurgeryType> surgeryTypes;
    @ManyToMany
    @JoinTable(name = "clinics_examTypes", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "examType_id", referencedColumnName = "id"))
    private Set<ExamType> examTypes;


    public Clinic() {
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
}
