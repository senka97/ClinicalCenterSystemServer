package team57.project.dto;

public class DoctorSearch {

    private Long id;
    private String name;
    private String surname;
    private String serialNumber;

    public DoctorSearch(){

    }

    public DoctorSearch(Long id, String name, String surname, String searialNumber){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.serialNumber = searialNumber;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
