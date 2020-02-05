package team57.project.dto;

public class Month {

    private String month;
    private int number;

    public Month(){

    }

    public Month(String month,int number){
        this.month = month;
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
