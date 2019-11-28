package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
