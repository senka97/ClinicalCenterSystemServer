package team57.project.dto;

import team57.project.model.Doctor;

public class DoctorRating {

    private Long id;
    private String name;
    private String surname;
    private double rating;
    private double numberOfReviews;
    private String serialNumber;

    public DoctorRating(){

    }

    public DoctorRating(Doctor doctor){
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.surname = doctor.getSurname();
        this.rating = doctor.getRating();
        this.numberOfReviews = doctor.getNumberOfReviews();
        this.serialNumber = doctor.getSerialNumber();
    }

    public double getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(double numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
