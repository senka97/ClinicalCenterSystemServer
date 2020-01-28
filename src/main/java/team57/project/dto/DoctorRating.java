package team57.project.dto;

import team57.project.model.Doctor;

public class DoctorRating {

    private Long id;
    private String name;
    private String surname;
    private double rating;

    public DoctorRating(){

    }

    public DoctorRating(Doctor doctor){
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.surname = doctor.getSurname();
        this.rating = doctor.getRating();
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
}
