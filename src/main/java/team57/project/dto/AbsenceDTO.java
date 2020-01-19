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
    private Long idEmployee;
    private String employee; //Doctor, Nurse
    private String employeeName;
    private String employeeSurname;
    private String employeeSerialNumber;

    public AbsenceDTO(){

    }

    public AbsenceDTO(Long id, String typeOfAbsence, String statusOfAbsence, LocalDate startDate, LocalDate endDate, Long idEmployee, String employee, String employeeName, String employeeSurname, String employeeSerialNumber) {
        this.id = id;
        this.typeOfAbsence = typeOfAbsence;
        this.statusOfAbsence = statusOfAbsence;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idEmployee = idEmployee;
        this.employee = employee;
        this.employeeName = employeeName;
        this.employeeSurname = employeeSurname;
        this.employeeSerialNumber = employeeSerialNumber;
    }

    public AbsenceDTO(Absence absence){
        this.id = absence.getId();
        this.typeOfAbsence = absence.getTypeOfAbsence();
        this.statusOfAbsence = absence.getStatusOfAbsence();
        this.startDate = absence.getStartDate();
        this.endDate = absence.getEndDate();
        if(absence.getDoctor() != null) {
            this.employee = "Doctor";
            this.idEmployee = absence.getDoctor().getId();
            this.employeeName = absence.getDoctor().getName();
            this.employeeSurname = absence.getDoctor().getSurname();
            this.employeeSerialNumber = absence.getDoctor().getSerialNumber();
        }else {
            this.employee = "Nurse";
            this.idEmployee = absence.getNurse().getId();
            this.employeeName = absence.getNurse().getName();
            this.employeeSurname = absence.getNurse().getSurname();
            this.employeeSerialNumber = absence.getNurse().getSerialNumber();
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

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public String getEmployeeSerialNumber() {
        return employeeSerialNumber;
    }

    public void setEmployeeSerialNumber(String employeeSerialNumber) {
        this.employeeSerialNumber = employeeSerialNumber;
    }
}
