package team57.project.dto;

import team57.project.model.MedicalExam;

import java.time.LocalDate;
import java.time.LocalTime;

public class MedicalExamRequest {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private String fullNamePatient;
    private String fullNameDoctor;
    private Long idDoctor;
    private String examTypeName;

    public MedicalExamRequest(){

    }

    public MedicalExamRequest(MedicalExam me){
        this.id = me.getId();
        this.date = me.getDate();
        this.startTime = me.getStartTime();
        this.fullNamePatient = me.getPatient().getName() + " " + me.getPatient().getSurname();
        this.fullNameDoctor = me.getDoctor().getName() + " " + me.getDoctor().getSurname();
        this.idDoctor = me.getDoctor().getId();
        this.examTypeName = me.getExamType().getName();
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

    public String getFullNameDoctor() {
        return fullNameDoctor;
    }

    public void setFullNameDoctor(String fullNameDoctor) {
        this.fullNameDoctor = fullNameDoctor;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public Long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Long idDoctor) {
        this.idDoctor = idDoctor;
    }
}
