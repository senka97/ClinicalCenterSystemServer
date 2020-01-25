package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.MedicalExam;

import java.util.List;

public interface MedicalExamRepository extends JpaRepository<MedicalExam, Long> {

    @Query(value = "SELECT * FROM medical_exam e WHERE e.patient_id = ?1", nativeQuery = true)
    List<MedicalExam> findMedicalExamByPatient(Long patientId);
}
