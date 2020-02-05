package team57.project.dto;

public class Hour {

    private String hour;
    private int number;

    public Hour(){

    }

    public Hour(String hour, int number) {
        this.hour = hour;
        this.number = number;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
