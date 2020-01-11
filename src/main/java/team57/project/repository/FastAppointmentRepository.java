package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.FastAppointment;
import team57.project.model.Room;

import java.time.LocalDateTime;
import java.util.Collection;

public interface FastAppointmentRepository extends JpaRepository<FastAppointment, Long> {

    //@Query("SELECT fa FROM fast_appointment fa WHERE fa.exam_type_id = ?1 AND fa.date_time > ?2")
    //Collection<FastAppointment> findFAWithExamType(Long examTypeId, LocalDateTime now);


}
