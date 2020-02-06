package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.TermDoctor;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TermDoctorRepository extends JpaRepository<TermDoctor,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TermDoctor> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select t from TermDoctor t where t.doctor.id=?3 and t.dateTerm=?1 and t.startTime=?2")
    TermDoctor findTermDoctor(LocalDate date, LocalTime time, Long idDoctor);

    @Query(value = "select t from TermDoctor t where t.doctor.id=?1 and t.dateTerm>=?2 and t.dateTerm<=?3 and t.free=false")
    List<TermDoctor> checkScheduledTerms(Long id, LocalDate start, LocalDate end);

    @Query(value = "select distinct t FROM TermDoctor as t inner join t.doctor d inner join d.examTypes e where t.doctor.id=?1 and e.id=?2 and  t.dateTerm = ?3 and t.free=true")
    List<TermDoctor> getFreeTerms(Long doctorId, Long idET, LocalDate date);

    @Query(value = "select t from TermDoctor t inner join t.doctor d where d.id=?1 and t.dateTerm=?2 and t.free=true")
    List<TermDoctor> findFreeTermsDate(Long doctorId,  LocalDate dat);



}
