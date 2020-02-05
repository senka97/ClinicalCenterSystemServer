package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Clinic;
import team57.project.model.FastAppointment;
import team57.project.model.MedicalExam;
import team57.project.model.Surgery;

import java.time.LocalDate;
import java.util.List;

public interface ClinicRepository  extends JpaRepository<Clinic, Long> {

     Clinic findByName(String name);

     //@Query(value = "SELECT Distinct clinic_id FROM clinic_patients c WHERE c.patient_id = ?1", nativeQuery = true)
     //List<Long> patientClinics(Long patientId);
     @Query(value = "select distinct c.id from Clinic c inner join c.patients p where p.id=?1")
     List<Long> patientClinics(Long patientId);

     @Query(value = "select DISTINCT c from Clinic as c inner join c.doctors d inner join d.terms t inner join d.examTypes e where e.id=?1 and  t.dateTerm = ?2 and t.free=true")
     List<Clinic> getFreeClinics(Long idET, LocalDate date);

     @Query(value = "select fa from Clinic c inner join c.fastAppointments fa where c.id=?1 and fa.dateFA>=?2 and fa.dateFA<=?3")
     List<FastAppointment> findFAIncome(Long id, LocalDate startDate, LocalDate endDate);

     @Query(value = "select me from Clinic c inner join c.medicalExams me where c.id=?1 and me.date>=?2 and me.date<=?3")
     List<MedicalExam> findMEIncome(Long id, LocalDate startDate, LocalDate endDate);

     @Query(value = "select s from Clinic c inner join c.surgeries s where c.id=?1 and s.date>=?2 and s.date<=?3")
     List<Surgery> findSIncome(Long id, LocalDate startDate, LocalDate endDate);

     @Query(value = "select me from Clinic c inner join c.medicalExams me where c.id=?1 and me.date=?2")
     List<MedicalExam> getAllMedicalExams(Long id,LocalDate date);

     @Query(value = "select fa from Clinic c inner join c.fastAppointments fa where c.id=?1 and fa.dateFA=?2")
     List<FastAppointment> getAllFastAppointments(Long id,LocalDate date);

     @Query(value = "select me from Clinic c inner join c.medicalExams me where c.id=?1 and me.date>=?2 and me.date<=?3")
     List<MedicalExam> findMEDate(Long id,LocalDate start,LocalDate end);

     @Query(value = "select fa from Clinic c inner join c.fastAppointments fa where c.id=?1 and fa.dateFA>=?2 and fa.dateFA<=?3")
     List<FastAppointment> findFADate(Long id,LocalDate start,LocalDate end);
}
