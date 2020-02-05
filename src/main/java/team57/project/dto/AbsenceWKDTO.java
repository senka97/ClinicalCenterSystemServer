package team57.project.dto;

import team57.project.model.Absence;

import java.time.LocalDate;

public class AbsenceWKDTO {
    private Long id;
    private String typeOfAbsence;
    private LocalDate startDate;
    private LocalDate endDate;


    public AbsenceWKDTO(Absence a)
    {
        this.id = a.getId();
        this.typeOfAbsence = a.getTypeOfAbsence();
        this.startDate = a.getStartDate();
        this.endDate =a.getEndDate();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOfAbsence() {
        return typeOfAbsence;
    }

    public void setTypeOfAbsence(String typeOfAbsence) {
        this.typeOfAbsence = typeOfAbsence;
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

}
