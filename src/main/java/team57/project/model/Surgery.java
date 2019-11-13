package team57.project.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Entity
public class Surgery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "startTime", nullable = false)
    private Time startTime;
    @Column(name = "endTime", nullable = false)
    private Time endTime;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SurgeryType surgeryType;
    @ManyToMany(mappedBy = "surgeries")
    private Set<Doctor> doctors;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;
}
