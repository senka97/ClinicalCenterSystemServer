package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.MedicalRecord;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}