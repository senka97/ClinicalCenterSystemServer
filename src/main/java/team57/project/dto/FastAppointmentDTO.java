package team57.project.dto;

import team57.project.model.FastAppointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class FastAppointmentDTO {

    private LocalDate date;
    private LocalTime time;
    private String nameExamType;
    private String fullNameDoctor;
    private String fullNamePatient;
    private String nameAndNumberRoom;
    private double price;
    private double discount;

    public FastAppointmentDTO(){

    }

    public FastAppointmentDTO(FastAppointment fa){
        this.date = fa.getDateFA();
        this.time = fa.getTimeFA();
        this.nameExamType = fa.getExamType().getName();
        this.fullNameDoctor = fa.getDoctor().getName() + " " + fa.getDoctor().getSurname();
        if(fa.getPatient() != null){
            this.fullNamePatient = fa.getPatient().getName() + " " + fa.getPatient().getSurname();
        }else{
            this.fullNamePatient = "None";
        }
        this.nameAndNumberRoom = fa.getRoom().getName() + " (No. " + fa.getRoom().getNumber() +")";
        this.price = fa.getPrice();
        this.discount = fa.getDiscount();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getNameExamType() {
        return nameExamType;
    }

    public void setNameExamType(String nameExamType) {
        this.nameExamType = nameExamType;
    }

    public String getFullNameDoctor() {
        return fullNameDoctor;
    }

    public void setFullNameDoctor(String fullNameDoctor) {
        this.fullNameDoctor = fullNameDoctor;
    }

    public String getNameAndNumberRoom() {
        return nameAndNumberRoom;
    }

    public void setNameAndNumberRoom(String nameAndNumberRoom) {
        this.nameAndNumberRoom = nameAndNumberRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getFullNamePatient() {
        return fullNamePatient;
    }

    public void setFullNamePatient(String fullNamePatient) {
        this.fullNamePatient = fullNamePatient;
    }
}
