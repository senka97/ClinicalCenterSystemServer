package team57.project.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToMany(mappedBy = "chronicConditions")
    private Set<MedicalRecord> records;
    public Diagnose()
    {

    }

    public Diagnose(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
