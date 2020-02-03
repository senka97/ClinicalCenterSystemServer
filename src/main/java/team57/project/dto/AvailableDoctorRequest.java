package team57.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailableDoctorRequest {

    private Long idExamType;
    private LocalDate date;
    private LocalTime time;

    public AvailableDoctorRequest(){

    }

    public AvailableDoctorRequest(Long idExamType, LocalDate date, LocalTime time){
        this.idExamType = idExamType;
        this.date = date;
        this.time = time;
    }

    public Long getIdExamType() {
        return idExamType;
    }

    public void setIdExamType(Long idExamType) {
        this.idExamType = idExamType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
