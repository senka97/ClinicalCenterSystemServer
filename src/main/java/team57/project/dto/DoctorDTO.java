package team57.project.dto;

import team57.project.model.Doctor;

import java.time.LocalTime;

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

    //private int startWorkHour;
    //private int startWorkMinute;
    //private int endWorkHour;
    //private int endWorkMinute;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;

    public DoctorDTO(){

    }

    public DoctorDTO(Long id, String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber, int startWorkHour, int startWorkMinute, int endWorkHour, int endWorkMinute) {
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
        //this.startWorkHour = startWorkHour;
        //this.startWorkMinute = startWorkMinute;
        //this.endWorkHour = endWorkHour;
        //this.endWorkMinute = endWorkMinute;
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
        //this.startWorkHour = doctor.getWorkingHoursStart().getHour();
        //this.startWorkMinute = doctor.getWorkingHoursStart().getMinute();
        //this.endWorkHour = doctor.getWorkingHoursEnd().getHour();
        //this.endWorkMinute = doctor.getWorkingHoursEnd().getMinute();
        this.workingHoursStart = doctor.getWorkingHoursStart();
        this.workingHoursEnd = doctor.getWorkingHoursEnd();
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

    /*public int getStartWorkHour() {
        return startWorkHour;
    }

    public void setStartWorkHour(int startWorkHour) {
        this.startWorkHour = startWorkHour;
    }

    public int getStartWorkMinute() {
        return startWorkMinute;
    }

    public void setStartWorkMinute(int startWorkMinute) {
        this.startWorkMinute = startWorkMinute;
    }

    public int getEndWorkHour() {
        return endWorkHour;
    }

    public void setEndWorkHour(int endWorkHour) {
        this.endWorkHour = endWorkHour;
    }

    public int getEndWorkMinute() {
        return endWorkMinute;
    }

    public void setEndWorkMinute(int endWorkMinute) {
        this.endWorkMinute = endWorkMinute;
    }*/

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
}
