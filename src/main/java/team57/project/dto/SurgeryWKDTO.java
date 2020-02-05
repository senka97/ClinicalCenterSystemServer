package team57.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import team57.project.model.Surgery;

public class SurgeryWKDTO {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String nameSurgeryType;
    private String nameAndNumberRoom;
    private UserDTO patient;

    public SurgeryWKDTO(Surgery s)
    {
        this.id = s.getId();
        this.date = s.getDate();
        this.time = s.getStartTime();
        this.nameSurgeryType = s.getSurgeryType().getName();
        if(s.getPatient() != null){
            this.patient = new UserDTO(s.getPatient());
        }else{
            this.patient = null;
        }
        this.nameAndNumberRoom = s.getSurgeryRoom().getName() + " (No. " + s.getSurgeryRoom().getNumber() +")";
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

    public String getNameSurgeryType() {
        return nameSurgeryType;
    }

    public void setNameSurgeryType(String nameSurgeryType) {
        this.nameSurgeryType = nameSurgeryType;
    }

    public String getNameAndNumberRoom() {
        return nameAndNumberRoom;
    }

    public void setNameAndNumberRoom(String nameAndNumberRoom) {
        this.nameAndNumberRoom = nameAndNumberRoom;
    }

    public UserDTO getPatient() {
        return patient;
    }

    public void setPatient(UserDTO patient) {
        this.patient = patient;
    }
}
