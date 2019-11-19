package team57.project.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {

    public Doctor(){
        super();
    }

    @Column(name = "rating", nullable = false, columnDefinition = "double default -1")
    private double rating; //( rating  * reviews + new rating)/(reviews + 1)
    @Column(name = "numberOfReviews", nullable = false, columnDefinition = "bigint default -1")
    private long numberOfReviews; //number of reviews
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;
    @ManyToMany
    @JoinTable(name = "doctors_examTypes", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "examType_id", referencedColumnName = "id"))
    private Set<ExamType> examTypes; //type of medical examination
    @ManyToMany
    @JoinTable(name = "doctors_surgeries", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "surgery_id", referencedColumnName = "id"))
    private Set<Surgery> surgeries;
    @ManyToMany
    @JoinTable(name = "doctors_surgeryTypes", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "surgeryType_id", referencedColumnName = "id"))
    private Set<SurgeryType> surgeryTypes;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalExam> medicalExams;

}
