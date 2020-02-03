package team57.project.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TermRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="dateTerm",nullable = false)
    private LocalDate dateTerm;
    @Column(name="startTime", nullable = false)
    private LocalTime startTime;
    @Column(name="endTime", nullable = false)
    private LocalTime endTime;
    @Column(name="free", nullable = false)
    private boolean free;
    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private Room room;

    public TermRoom(){

    }

    public TermRoom(LocalDate dateTerm, LocalTime startTime, LocalTime endTime, boolean free, Room room){
        this.dateTerm = dateTerm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.free = free;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTerm() {
        return dateTerm;
    }

    public void setDateTerm(LocalDate dateTerm) {
        this.dateTerm = dateTerm;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
