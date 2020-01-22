package team57.project.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;
@Entity
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "time", nullable = false)
    private Time time;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Diagnose> diagnoses;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Prescription> prescriptions;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

}
