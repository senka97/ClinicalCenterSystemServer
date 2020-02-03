package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.MedicalReport;
import team57.project.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByEmail(String email);

    @Query(value = "SELECT * FROM User p WHERE p.enabled = true AND p.type = 'PATIENT'", nativeQuery = true)
    List<Patient> findAll();

    @Query(value = "select distinct p.city from Patient p")
    List<String> getAllCities();




}
