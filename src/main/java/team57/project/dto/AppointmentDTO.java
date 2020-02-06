package team57.project.dto;

import lombok.Getter;
import lombok.Setter;
import team57.project.model.TermDoctor;

import java.time.LocalDate;

public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private String time;
    private String type;
    private Long doctorId;

    public AppointmentDTO(){}
    public AppointmentDTO(TermDoctor termDoc){
        this.id = termDoc.getId();
        this.date = termDoc.getDateTerm();
        this.time = termDoc.getStartTime().toString();
        this.doctorId = termDoc.getDoctor().getId();
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
