package team57.project.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "roomType", nullable = false)
    private String roomType; //Medical exam or Surgery
    @Column(name = "occupied", nullable = false)
    private boolean occupied;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Clinic clinic;
    //date and time of reservation
    //Calendar


    public Room(){

    }

    public Room(String name, String number, String roomType, boolean occupied) {
        this.name = name;
        this.number = number;
        this.roomType = roomType;
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    /*public Clinic getClinic() {
        return clinic;
    }*/

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /*public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }*/
}
