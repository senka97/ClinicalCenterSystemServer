package team57.project.dto;
import team57.project.model.Doctor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String serialNumber;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;

    private List<Long> examTypesId; //ovo za registrovanje doktora
    private List<Long> surgeryTypesId;

    public DoctorDTO(){

    }

    public DoctorDTO(Long id, String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber, LocalTime workingHoursStart, LocalTime workingHoursEnd) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.serialNumber = serialNumber;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.examTypesId = new ArrayList<Long>();
        this.surgeryTypesId = new ArrayList<Long>();

    }

    public DoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.surname = doctor.getSurname();
        this.email = doctor.getEmail();
        this.address = doctor.getAddress();
        this.city = doctor.getCity();
        this.country = doctor.getCountry();
        this.phoneNumber = doctor.getPhoneNumber();
        this.serialNumber = doctor.getSerialNumber();
        this.workingHoursStart = doctor.getWorkingHoursStart();
        this.workingHoursEnd = doctor.getWorkingHoursEnd();
        this.examTypesId = new ArrayList<Long>();
        this.surgeryTypesId = new ArrayList<Long>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public List<Long> getExamTypesId() {
        return examTypesId;
    }

    public void setExamTypesId(List<Long> examTypesId) {
        this.examTypesId = examTypesId;
    }

    public List<Long> getSurgeryTypesId() {
        return surgeryTypesId;
    }

    public void setSurgeryTypesId(List<Long> surgeryTypesId) {
        this.surgeryTypesId = surgeryTypesId;
    }

}