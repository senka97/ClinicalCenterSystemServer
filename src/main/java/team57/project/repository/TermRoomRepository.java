package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.TermDoctor;
import team57.project.model.TermRoom;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;

public interface TermRoomRepository extends JpaRepository<TermRoom,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select t from TermRoom t where t.room.id=?3 and t.dateTerm=?1 and t.startTime=?2")
    TermRoom findTermRoom(LocalDate date, LocalTime time, Long idRoom);
}
