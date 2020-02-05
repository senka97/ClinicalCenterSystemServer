package team57.project.dto;
import team57.project.model.MedicalExam;
import java.time.LocalDate;
import java.time.LocalTime;

public class MedicalExamWKDTO {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String nameExamType;
    private String nameAndNumberRoom;
    private UserDTO patient;

    public MedicalExamWKDTO(MedicalExam me)
    {
        this.id = me.getId();
        this.date = me.getDate();
        this.time = me.getStartTime();
        this.nameExamType = me.getExamType().getName();
        if(me.getPatient() != null){
            this.patient = new UserDTO(me.getPatient());
        }else{
            this.patient = null;
        }
        this.nameAndNumberRoom = me.getExamRoom().getName() + " (No. " + me.getExamRoom().getNumber() +")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNameAndNumberRoom() {
        return nameAndNumberRoom;
    }

    public void setNameAndNumberRoom(String nameAndNumberRoom) {
        this.nameAndNumberRoom = nameAndNumberRoom;
    }

    public UserDTO getPatient() {
        return patient;
    }

    public void setPatient(UserDTO patient) {
        this.patient = patient;
    }
}
