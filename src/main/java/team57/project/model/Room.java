package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "removed", nullable = false)
    private boolean removed;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TermRoom> terms;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RoomReservationTime> roomReservationTimes;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Clinic clinic;
    //date and time of reservation
    //Calendar


    public Room(){

    }

    public Room(String name, String number, String roomType, boolean removed) {
        this.name = name;
        this.number = number;
        this.roomType = roomType;
        this.removed = removed;
        this.terms = new HashSet<TermRoom>();
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /*public Clinic getClinic() {
        return clinic;
    }*/
     /*public void setClinic(Clinic clinic) {
        this.clinic = clinic;
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

    public Set<TermRoom> getTerms() {
        return terms;
    }

    public void setTerms(Set<TermRoom> terms) {
        this.terms = terms;
    }

    @JsonIgnore
    public Set<RoomReservationTime> getRoomReservationTimes() {
        return roomReservationTimes;
    }

    public void setRoomReservationTimes(Set<RoomReservationTime> roomReservationTimes) {
        this.roomReservationTimes = roomReservationTimes;
    }
}
