package team57.project.model;

import javax.persistence.*;
import java.util.Set;
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "verified", nullable = false)
    private boolean verified;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Medication> medications;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Nurse nurse;
}