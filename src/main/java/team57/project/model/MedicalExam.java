package team57.project.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
@Entity
public class MedicalExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "startTime", nullable = false)
    private Time startTime;
    @Column(name = "endTime", nullable = false)
    private Time endTime;
    @Column(name = "reserved", nullable = false)
    private Boolean reserved;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ExamType examType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room examRoom;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;



}
