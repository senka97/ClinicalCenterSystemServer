package team57.project.dto;

public class Week {

    private String week;
    private int number;

    public Week(){

    }

    public Week(String week, int number) {
        this.week = week;
        this.number = number;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
