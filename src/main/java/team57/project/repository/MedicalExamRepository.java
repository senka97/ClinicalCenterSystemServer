package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.MedicalExam;
import team57.project.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MedicalExamRepository extends JpaRepository<MedicalExam, Long> {

    @Query(value = "SELECT * FROM medical_exam e WHERE e.patient_id = ?1", nativeQuery = true)
    List<MedicalExam> findMedicalExamByPatient(Long patientId);

    //all unique doctors for each patient
    @Query(value = "SELECT DISTINCT doctor_id FROM medical_exam e WHERE e.patient_id = ?1", nativeQuery = true)
    List<Long> findDoctors(Long patientId);

    @Query(value = "select count(m) from Clinic c inner join c.medicalExams m where c.id=?1 and m.statusME='REQUESTED' and m.examRoom is null")
    double getNumExamRequests(Long id);

    @Query(value = "select m from Clinic c inner join c.medicalExams m where c.id=?1 and m.statusME='REQUESTED' and m.examRoom is null")
    List<MedicalExam> findExamRequests(Long id);

    @Query(value = "select r from Clinic c inner join c.rooms r inner join r.terms t where c.id=?1 and t.dateTerm=?2 and t.startTime=?3 and t.endTime=?4 and t.free=true and r.roomType='Medical exam' and r.removed=false")
    List<Room> findAvailableRooms(Long idClinic, LocalDate date, LocalTime startTime, LocalTime endTime);

    @Query(value = "select me from Clinic c inner join c.medicalExams me inner join me.examType et where c.id=?1 and me.statusME <> 'REJECTED' and et.id=?2 and (me.date>?3 or (me.date = ?3 and me.endTime>?4))")
    List<MedicalExam> findExamsWithType(Long idClinic,Long id,LocalDate nowDate,LocalTime nowTime);
}
