package team57.project.dto;

import team57.project.model.ExamType;

public class ExamTypeDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private double discount;

    public ExamTypeDTO(){

    }

    public ExamTypeDTO(ExamType examType){
        this.id = examType.getId();
        this.name = examType.getName();
        this.description = examType.getDescription();
        this.price = examType.getPrice();
        this.discount = examType.getDiscount();
    }

    public ExamTypeDTO(Long id, String name, String description, double price, double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
