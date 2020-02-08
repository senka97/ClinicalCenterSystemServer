package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Doctor;
import team57.project.model.TermDoctor;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);
    Doctor findBySerialNumber(String serialNumber);

    //@Query(value = "SELECT * FROM User d WHERE d.clinic_id = ?1 AND d.type = 'DOCTOR'", nativeQuery = true)
    @Query(value = "select d from Doctor d where d.clinic.id = ?1")
    List<Doctor> findDoctors(Long idClinic);


   @Query(value = "SELECT * FROM User d WHERE d.clinic_id = ?1 AND d.type = 'DOCTOR'", nativeQuery = true)
   List<Doctor> searchDoctors(Long idClinic,String name, String surname);

    @Query(value = "select d from Clinic as c inner join c.doctors d inner join d.terms t inner join d.examTypes e where c.id=?1 and e.id=?2 and  t.dateTerm = ?3 and t.startTime = ?4 and t.free=true")
    List<Doctor> getAvailableDoctors(Long idClinic, Long idET, LocalDate date, LocalTime time);


    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select d from Doctor d where d.id=?1")
    Doctor findDoctor(Long idDoctor);


    @Query(value = "select distinct d from Clinic as c inner join c.doctors d inner join d.terms t inner join d.examTypes e where c.id=?1 and e.id=?2 and  t.dateTerm = ?3 and t.free=true")
    List<Doctor> getFreeDoctors(Long idClinic, Long idET, LocalDate date);

    @Query(value = "select t from Doctor d inner join d.terms t where d.id=?1 and ((t.dateTerm>?2) or (t.dateTerm=?2 and t.endTime > ?3)) and t.free = false")
    List<TermDoctor> findScheduledTerms(Long id,LocalDate nowDate,LocalTime nowTime);

    @Query(value = "select d from Clinic c inner join c.doctors d inner join d.examTypes et where c.id=?1 and et.id=?2")
    List<Doctor> searchDoctorsExamType(Long idClinic,Long idExamType);

   // @Query(value = "select d from Doctor d inner join d.terms t where t.date =?1 and t.startTime =?2 ")
    @Query(value="select * from user u, doctors_surgery_types dst, term_doctor td  where u.type='DOCTOR' and u.id=dst.doctor_id and dst.surgery_type_id=?3 and td.doctor_id=u.id and td.free=true and td.date_term=?1 and td.start_time=?2", nativeQuery = true)
    List<Doctor> getFreeDoctorsForThisTerm(LocalDate date, LocalTime time, Long idSt);

    @Query(value = "select d from Clinic c inner join c.doctors d inner join d.surgeryTypes st where c.id=1 and st.id=?1")
    List<Doctor> getDoctorsSurgeryType(Long idSt);
}
