package team57.project.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="typeOfAbsence", nullable=false) //Paid vacation or sick leave
    private String typeOfAbsence;
    @Column(name="startDate", nullable=false)
    private LocalDate startDate;
    @Column(name="endDate", nullable=false)
    private LocalDate endDate;

    public Absence(){
    }

    public Absence(String typeOfAbsence, LocalDate startDate, LocalDate endDate) {
        this.typeOfAbsence = typeOfAbsence;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTypeOfAbsence() {
        return typeOfAbsence;
    }

    public void setTypeOfAbsence(String typeOfAbsence) {
        this.typeOfAbsence = typeOfAbsence;
    }
}
