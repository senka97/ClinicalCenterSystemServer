package team57.project.model;

import javax.persistence.*;
import java.util.Set;
@Entity
public class ExamType {
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
    @ManyToMany(mappedBy = "examTypes")
    private Set<Doctor> doctors;
    @ManyToMany(mappedBy = "examTypes")
    private Set<Clinic> clinics;

}
