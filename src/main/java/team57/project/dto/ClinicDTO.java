package team57.project.dto;

import team57.project.model.Clinic;

public class ClinicDTO {

    private Long id;
    private String name;
    private String address;
    private String description;
    private double rating;
    private long numberOfReviews;

    public ClinicDTO() {
    }

    public ClinicDTO(Clinic c){
        this(c.getId(), c.getName(),c.getAddress(),c.getDescription(),c.getRating(),c.getNumberOfReviews());
    }

    public ClinicDTO(Long id, String name, String address, String description, double rating, long numberOfReviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(long numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }
}
