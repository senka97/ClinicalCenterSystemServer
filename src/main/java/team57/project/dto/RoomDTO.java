package team57.project.dto;

import team57.project.model.Room;

public class RoomDTO {

    private Long id;
    private String name;
    private String number;
    private String roomType;
    private boolean occupied;

    public RoomDTO(){

    }

    public RoomDTO(Room r){
        this(r.getId(),r.getName(),r.getNumber(),r.getRoomType(),r.isOccupied());
    }

    public RoomDTO(Long id, String name, String number, String roomType, boolean occupied){
        this.id = id;
        this.name = name;
        this.number = number;
        this.roomType = roomType;
        this.occupied = occupied;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
