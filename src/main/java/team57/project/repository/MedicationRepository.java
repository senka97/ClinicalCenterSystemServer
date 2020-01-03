package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Medication findByCode(String code);
}
