package team57.project.dto;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailableRoomRequest {

    private LocalDate date;
    private LocalTime time;

    public AvailableRoomRequest(){

    }

    public AvailableRoomRequest(LocalDate date, LocalTime time){
        this.date = date;
        this.time = time;
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
