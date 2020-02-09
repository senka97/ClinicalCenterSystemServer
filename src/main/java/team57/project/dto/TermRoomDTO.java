package team57.project.dto;

import team57.project.model.TermRoom;

import java.time.LocalDate;
import java.time.LocalTime;

public class TermRoomDTO {

    Long id;
    private LocalDate date;
    private LocalTime time;

    public TermRoomDTO(){

    }

    public TermRoomDTO(TermRoom tr){
        this.id = tr.getId();
        this.date = tr.getDateTerm();
        this.time = tr.getStartTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

