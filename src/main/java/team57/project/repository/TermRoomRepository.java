package team57.project.repository;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.TermDoctor;
import team57.project.model.TermRoom;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TermRoomRepository extends JpaRepository<TermRoom,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select t from TermRoom t where t.room.id=?3 and t.dateTerm=?1 and t.startTime=?2")
    TermRoom findTermRoom(LocalDate date, LocalTime time, Long idRoom);

    @Query(value = "select t from TermRoom t inner join t.room r where r.id=?1 and t.dateTerm=?2 and t.free=true and t.startTime>=?3 and t.endTime<=?4")
    List<TermRoom> findFreeTermsDate(Long roomId, LocalDate dat, LocalTime start,LocalTime end);

    @Query(value = "select t from TermRoom t inner join t.room r where r.id=?1 and t.dateTerm=?2 and t.free=true")
    List<TermRoom> findFreeTermsJustDate(Long roomId, LocalDate dat);

   // @Query(value = "SELECT * FROM clinic c, clinic_rooms cr, room r, term_room tr where c.id=?1 and c.id = cr.clinic_id and cr.rooms_id = r.id and r.room_type = 'Surgery' and tr.room_id=r.id and  tr.date_term=?2 and tr.free=true" , nativeQuery = true)
    @Query(value= "SELECT * FROM term_room tr , room r, clinic_rooms cr ,clinic c  where tr.date_term=?2 and tr.free=true and r.id = tr.room_id and r.room_type = 'Surgery' and cr.rooms_id = r.id and c.id = cr.clinic_id and c.id=?1", nativeQuery = true)
    List<TermRoom> findAvailableRooms(Long id, LocalDate date);

    @Query(value="select * from term_room tr where tr.date_term=?1 and tr.start_time=?2 and tr.room_id= ?3", nativeQuery = true)
    TermRoom findByDateTime(LocalDate date, LocalTime time, Long id);
}
