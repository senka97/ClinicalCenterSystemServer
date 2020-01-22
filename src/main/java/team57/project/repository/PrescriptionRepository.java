package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Prescription;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query(value = "SELECT * FROM Prescription p WHERE p.verified = false AND p.clinic_id = ?1", nativeQuery = true)
    List<Prescription> findUnverified(Long id);
}