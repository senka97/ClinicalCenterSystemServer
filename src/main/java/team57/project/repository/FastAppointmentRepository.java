package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.FastAppointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FastAppointmentRepository extends JpaRepository<FastAppointment, Long> {

    //@Query("SELECT fa FROM fast_appointment fa WHERE fa.exam_type_id = ?1 AND fa.date_time > ?2")
    //Collection<FastAppointment> findFAWithExamType(Long examTypeId, LocalDateTime now);

    @Query(value="select f from FastAppointment f where f.clinic.id=?1 and ((f.dateFA < ?2) or (f.dateFA = ?2 and f.timeFA<=?3))")
    List<FastAppointment> findFinished(Long id, LocalDate nowDate, LocalTime nowTime);

    @Query(value="select f from FastAppointment f where f.clinic.id=?1 and ((f.dateFA > ?2) or (f.dateFA = ?2 and f.timeFA>?3)) and f.patient is not null")
    List<FastAppointment> findScheduled(Long id, LocalDate nowDate, LocalTime nowTime);

    @Query(value="select f from FastAppointment f where f.clinic.id=?1 and ((f.dateFA > ?2) or (f.dateFA = ?2 and f.timeFA>?3)) and f.patient is null")
    List<FastAppointment> findFree(Long id, LocalDate nowDate, LocalTime nowTime);

    @Query(value="select * from fast_appointment f where f.doctor_id= ?1 " , nativeQuery = true)
    List<FastAppointment> findReserved(Long id);


}
