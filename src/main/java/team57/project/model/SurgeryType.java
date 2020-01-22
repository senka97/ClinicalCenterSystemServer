package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class SurgeryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "discount", nullable = false)
    private double discount;
    @ManyToMany(mappedBy = "surgeryTypes")
    private Set<Doctor> doctors;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clinics_surgeryTypes", joinColumns = @JoinColumn(name = "surgeryType_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"))
    private Set<Clinic> clinics;
    @Column(name = "removed", nullable = false)
    private boolean removed;

    public SurgeryType(){

    }

    public SurgeryType(String name, String description, double price, double discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.removed = false;
        this.doctors = new HashSet<>();
        this.clinics = new HashSet<>();
    }

    public SurgeryType(String name, String description, double price, double discount, Set<Doctor> doctors, Set<Clinic> clinics) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.doctors = doctors;
        this.clinics = clinics;
        this.removed = false;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @JsonIgnore
    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    @JsonIgnore
    public Set<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(Set<Clinic> clinics) {
        this.clinics = clinics;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
