package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Room;
import team57.project.model.TermDoctor;
import team57.project.model.TermRoom;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByNameAndNumber(String name,String number);

    @Query(value = "select r from Clinic as c inner join c.rooms r inner join r.terms t where c.id=?1 and  t.dateTerm = ?2 and t.startTime = ?3 and t.free=true")
    List<Room> getAvailableRooms(Long id, LocalDate date, LocalTime time);

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select r from Room r where r.id=?1")
    Room findRoom(Long idRoom);

    @Query(value = "select t from Room r inner join r.terms t where r.id=?1 and ((t.dateTerm>?2) or (t.dateTerm=?2 and t.endTime > ?3)) and t.free = false")
    List<TermRoom> findScheduledTerms(Long id, LocalDate nowDate, LocalTime nowTime);

    @Query(value = "select r from Clinic c inner join c.rooms r where c.id=?1 and r.removed=false and r.roomType='Medical exam'")
    List<Room> findRooms(Long clinicId);

    @Query(value = "select r from Clinic c inner join c.rooms r where c.id=?1")
    List<Room> findAllInClinic(Long id);
}
