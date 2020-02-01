package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Doctor;

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


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select d from Doctor d where d.id=?1")
    Doctor findDoctor(Long idDoctor);


    @Query(value = "select DISTINCT d from Clinic as c inner join c.doctors d inner join d.terms t inner join d.examTypes e where c.id=?1 and e.id=?2 and  t.dateTerm = ?3 and t.free=true")
    List<Doctor> getFreeDoctors(Long idClinic, Long idET, LocalDate date);


}
