package team57.project.dto;

import team57.project.model.Nurse;

public class NurseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String serialNumber;

    public NurseDTO(){

    }

    public NurseDTO(Long id, String name, String surname, String email, String address, String city, String country, String phoneNumber, String serialNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.serialNumber = serialNumber;
    }

    public NurseDTO(Nurse nurse){
        this.id = nurse.getId();
        this.name = nurse.getName();
        this.surname = nurse.getSurname();
        this.email = nurse.getEmail();
        this.address = nurse.getAddress();
        this.city = nurse.getCity();
        this.country = nurse.getCountry();
        this.phoneNumber = nurse.getPhoneNumber();
        this.serialNumber = nurse.getSerialNumber();
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
}
