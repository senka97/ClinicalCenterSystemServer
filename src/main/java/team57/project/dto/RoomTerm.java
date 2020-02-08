package team57.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import team57.project.model.Surgery;
import team57.project.model.TermRoom;

public class RoomTerm {

    private Long id;
    private Long idRoom;
    private String name;
    private String number;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomType;
    private Long idSurgeryType;
    private List<DoctorFA> doctors;

    public RoomTerm()
    {

    }

    public RoomTerm(TermRoom r)
    {
        this.id = r.getId();
        this.idRoom = r.getRoom().getId();
        this.name = r.getRoom().getName();
        this.number = r.getRoom().getNumber();
        this.roomType = r.getRoom().getRoomType();
        this.startTime =  r.getStartTime();
        this.endTime = r.getEndTime();
        this.date = r.getDateTerm();
    }

    public List<DoctorFA> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorFA> doctors) {
        this.doctors = doctors;
    }

    public Long getIdSurgeryType() {
        return idSurgeryType;
    }

    public void setIdSurgeryType(Long idRoomType) {
        this.idSurgeryType = idRoomType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
