package team57.project.dto;

import team57.project.model.Doctor;

public class DoctorFA {

    private Long id;
    private String fullName;

    public DoctorFA(){

    }

    public DoctorFA(Doctor doctor){
        this.id = doctor.getId();
        this.fullName = doctor.getName() + " " + doctor.getSurname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
