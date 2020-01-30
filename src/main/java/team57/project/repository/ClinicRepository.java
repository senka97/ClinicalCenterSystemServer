package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Clinic;

import java.util.List;

public interface ClinicRepository  extends JpaRepository<Clinic, Long> {

     Clinic findByName(String name);

     @Query(value = "SELECT Distinct clinic_id FROM clinic_patients c WHERE c.patient_id = ?1", nativeQuery = true)
     List<Long> patientClinics(Long patientId);
}
