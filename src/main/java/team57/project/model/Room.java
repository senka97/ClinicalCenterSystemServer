package team57.project.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "roomType", nullable = false)
    private String roomType; //enum 0-medicalExamination, 1-surgery
    @Column(name = "occupied", nullable = false)
    private boolean occupied;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;
    //date and time of reservation
    //Calendar

}
