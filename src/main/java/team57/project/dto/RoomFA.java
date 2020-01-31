package team57.project.dto;


import team57.project.model.Room;

public class RoomFA {

    private Long id;
    private String nameAndNumber;

    public RoomFA(){

    }

    public RoomFA(Long id, String nameAndNumber){
        this.id = id;
        this.nameAndNumber = nameAndNumber;
    }

    public RoomFA(Room room){
        this.id = room.getId();
        this.nameAndNumber = room.getName() + " (number " + room.getNumber() + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAndNumber() {
        return nameAndNumber;
    }

    public void setNameAndNumber(String nameAndNumber) {
        this.nameAndNumber = nameAndNumber;
    }
}
