package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.MedicalRecord;
import team57.project.model.MedicalReport;

import java.util.List;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}