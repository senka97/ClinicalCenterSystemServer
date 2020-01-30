package team57.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RoomReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;
    @Column(name = "startDateTime", nullable=false)
    private LocalDateTime startDateTime; //dd-MM-yyyy hh:mm 00:00 - 23:59
    @Column(name = "endDateTime", nullable=false)
    private LocalDateTime endDateTime;
    @Column(name = "done", nullable=false)
    private boolean done; //if that reseravation ended

    public RoomReservationTime() {

    }

    public RoomReservationTime(Room room, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean done) {
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
