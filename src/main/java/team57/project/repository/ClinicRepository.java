package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Clinic;

import java.time.LocalDate;
import java.util.List;

public interface ClinicRepository  extends JpaRepository<Clinic, Long> {

     Clinic findByName(String name);

     @Query(value = "SELECT Distinct clinic_id FROM clinic_patients c WHERE c.patient_id = ?1", nativeQuery = true)
     List<Long> patientClinics(Long patientId);

     @Query(value = "select DISTINCT c from Clinic as c inner join c.doctors d inner join d.terms t inner join d.examTypes e where e.id=?1 and  t.dateTerm = ?2 and t.free=true")
     List<Clinic> getFreeClinics(Long idET, LocalDate date);
}
