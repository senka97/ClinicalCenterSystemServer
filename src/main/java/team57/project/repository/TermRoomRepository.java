package team57.project.repository;

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
}
