package team57.project.dto;

import team57.project.model.Clinic;
import team57.project.model.ClinicAdmin;

public class ClinicAdminDTO {

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
    //private Clinic clinic;

    public ClinicAdminDTO() {

    }

    public ClinicAdminDTO(Long id, String name, String surname, String email, String password, String address, String city, String country, String phoneNumber, String serialNumber) {
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
        //this.clinic = clinic;
    }

    public ClinicAdminDTO(ClinicAdmin clinicAdmin) {
        this(clinicAdmin.getId(),clinicAdmin.getName(),clinicAdmin.getSurname(),clinicAdmin.getEmail(),clinicAdmin.getPassword(),clinicAdmin.getAddress(),
                clinicAdmin.getCity(),clinicAdmin.getCountry(),clinicAdmin.getPhoneNumber(),clinicAdmin.getSerialNumber());
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

    /*public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }*/
}
