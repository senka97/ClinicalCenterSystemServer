package team57.project.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

    @Column(name = "activatedAccount", nullable = false)
    private boolean activatedAccount;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="medicalRecord_id")
    private MedicalRecord medicalRecord;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Surgery> surgeries;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;
}
