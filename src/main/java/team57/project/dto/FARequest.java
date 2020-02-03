package team57.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FARequest {

    private LocalDate date;
    private LocalTime time;
    private Long idExamType;
    private Long idDoctor;
    private Long idRoom;

    public FARequest(){

    }

    public FARequest(LocalDate date, LocalTime time, Long idExamType, Long idDoctor, Long idRoom){
        this.date = date;
        this.time = time;
        this.idExamType = idExamType;
        this.idDoctor = idDoctor;
        this.idRoom = idRoom;
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

    public Long getIdExamType() {
        return idExamType;
    }

    public void setIdExamType(Long idExamType) {
        this.idExamType = idExamType;
    }

    public Long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Long idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }
}
