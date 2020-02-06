package team57.project.dto;

import team57.project.model.FastAppointment;

public class FastAppointmentPDTO {

    private Long id;
    private String date;
    private String startTime;
    private String examType;

    public FastAppointmentPDTO(FastAppointment e)
    {
        this.id=e.getId();
        this.date = e.getDateFA().toString();
        this.startTime = e.getTimeFA().toString();
        this.examType = e.getExamType().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
