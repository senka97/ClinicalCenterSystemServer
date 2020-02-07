package team57.project.dto;

import team57.project.model.Surgery;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SurgeryRequest {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private String fullNamePatient;
    private String surgeryTypeName;
    private Long idSurgeryType;
   // private List<DoctorFA> doctors;

    public SurgeryRequest(Surgery s)
    {
        this.id = s.getId();
        this.date = s.getDate();
        this.startTime = s.getStartTime();
        this.fullNamePatient = s.getPatient().getName() + " " + s.getPatient().getSurname();
        this.surgeryTypeName = s.getSurgeryType().getName();
        this.idSurgeryType = s.getSurgeryType().getId();
        //this.doctors = new ArrayList<>();
    }

   /* public List<DoctorFA> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorFA> doctors) {
        this.doctors = doctors;
    }*/

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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getFullNamePatient() {
        return fullNamePatient;
    }

    public void setFullNamePatient(String fullNamePatient) {
        this.fullNamePatient = fullNamePatient;
    }


    public String getSurgeryTypeName() {
        return surgeryTypeName;
    }

    public void setSurgeryTypeName(String surgeryTypeName) {
        this.surgeryTypeName = surgeryTypeName;
    }

    public Long getIdSurgeryType() {
        return idSurgeryType;
    }

    public void setIdSurgeryType(Long idSurgeryType) {
        this.idSurgeryType = idSurgeryType;
    }
}
