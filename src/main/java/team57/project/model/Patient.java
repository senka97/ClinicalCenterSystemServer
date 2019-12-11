package team57.project.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

    public Patient(){
        super();
    }
    @Column(name = "activatedAccount", nullable = false)
    private String activatedAccount; //ACCEPTED, REJECTED, UNRESOLVED
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="medicalRecord_id")
    private MedicalRecord medicalRecord;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Surgery> surgeries;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;

    public Patient(String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
        super(name, surname, email, password, address, city, country, phoneNumber, serialNumber);
    }

    public String getActivatedAccount() {
        return activatedAccount;
    }

    public void setActivatedAccount(String activatedAccount) {
        this.activatedAccount = activatedAccount;
    }
}
