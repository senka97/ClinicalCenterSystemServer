package team57.project.dto;

public class PriceTag {

    private String name;
    private double price;
    private double discount;
    private double currentPrice;

    public PriceTag(){

    }

    public PriceTag(String name, double price, double discount){
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.currentPrice = price - price*(discount/100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
