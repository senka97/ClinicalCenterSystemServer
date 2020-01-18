package team57.project.dto;

import team57.project.model.Absence;
import team57.project.model.Doctor;
import team57.project.model.Nurse;

import java.time.LocalDate;

public class AbsenceDTO {

    private Long id;
    private String typeOfAbsence;
    private String statusOfAbsence;
    private LocalDate startDate;
    private LocalDate endDate;
    private DoctorDTO doctorDTO;
    private NurseDTO nurseDTO;

    public AbsenceDTO(){

    }

    public AbsenceDTO(Long id, String typeOfAbsence, String statusOfAbsence, LocalDate startDate, LocalDate endDate, DoctorDTO doctorDTO, NurseDTO nurseDTO) {
        this.id = id;
        this.typeOfAbsence = typeOfAbsence;
        this.statusOfAbsence = statusOfAbsence;
        this.startDate = startDate;
        this.endDate = endDate;
        this.doctorDTO = doctorDTO;
        this.nurseDTO = nurseDTO;
    }

    public AbsenceDTO(Absence absence){
        this.id = absence.getId();
        this.typeOfAbsence = absence.getTypeOfAbsence();
        this.statusOfAbsence = absence.getStatusOfAbsence();
        this.startDate = absence.getStartDate();
        this.endDate = absence.getEndDate();
        if(absence.getDoctor() != null) {
            this.doctorDTO = new DoctorDTO(absence.getDoctor());
        }else {
            this.nurseDTO = new NurseDTO(absence.getNurse());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOfAbsence() {
        return typeOfAbsence;
    }

    public void setTypeOfAbsence(String typeOfAbsence) {
        this.typeOfAbsence = typeOfAbsence;
    }

    public String getStatusOfAbsence() {
        return statusOfAbsence;
    }

    public void setStatusOfAbsence(String statusOfAbsence) {
        this.statusOfAbsence = statusOfAbsence;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DoctorDTO getDoctorDTO() {
        return doctorDTO;
    }

    public void setDoctorDTO(DoctorDTO doctorDTO) {
        this.doctorDTO = doctorDTO;
    }

    public NurseDTO getNurseDTO() {
        return nurseDTO;
    }

    public void setNurseDTO(NurseDTO nurseDTO) {
        this.nurseDTO = nurseDTO;
    }
}
