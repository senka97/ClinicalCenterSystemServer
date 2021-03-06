package team57.project.dto;

import team57.project.model.Diagnose;
import team57.project.model.Doctor;
import team57.project.model.Medication;

import java.sql.Time;
import java.util.ArrayList;

public class MedicalReportDTO {

    private Long id;
    private String description;
    private String date;
    private String time;
    private DoctorDTO doctor;
    private ArrayList<Medication> medications;
    private ArrayList<Diagnose> diagnoses;
    private Long examId;
    private String type;

    public MedicalReportDTO()
    {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<Medication> medications) {
        this.medications = medications;
    }

    public ArrayList<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(ArrayList<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

}
