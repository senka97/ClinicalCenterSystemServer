package team57.project.dto;

import team57.project.model.MedicalExam;
import team57.project.model.Room;
import team57.project.model.TermDoctor;
import team57.project.model.TermRoom;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomME {

    private Long id;
    private String name;
    private String number;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public RoomME(){

    }

    public RoomME(Room r, MedicalExam me){
        this.id = r.getId();
        this.name = r.getName();
        this.number = r.getNumber();
        this.date = me.getDate();
        this.startTime = me.getStartTime();
        this.endTime = me.getEndTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
