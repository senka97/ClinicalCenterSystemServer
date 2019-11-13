package team57.project.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
@Entity
public class ClinicalCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequestForRegistration> requestForRegistrations;

}
