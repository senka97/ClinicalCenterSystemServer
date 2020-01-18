package team57.project.dto;

import java.time.LocalDate;

public class AbsenceRequest {

    private String typeOfAbsence;
    private LocalDate startDate;
    private LocalDate endDate;

    public AbsenceRequest(){

    }

    public AbsenceRequest(String typeOfAbsence, LocalDate startDate, LocalDate endDate) {
        this.typeOfAbsence = typeOfAbsence;
        this.startDate = startDate;
        this.endDate = endDate;
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
