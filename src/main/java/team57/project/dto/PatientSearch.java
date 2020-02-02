package team57.project.dto;

public class PatientSearch {

    private String serialNumber;
    private String name;
    private String surname;

    public PatientSearch(){
    }

    public PatientSearch(String serialNumber, String name, String surname){
        this.serialNumber = serialNumber;
        this.name = name;
        this.surname = surname;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
}
