package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.MedicalReport;

import java.util.List;

public interface MedicalReportRepository extends JpaRepository<MedicalReport,Long> {
    @Query(value = "SELECT * FROM medical_record_medical_reports mr JOIN medical_report mrp WHERE mr.medical_record_id= ?1 AND mrp.id = mr.medical_reports_id", nativeQuery = true)
    List<MedicalReport> getMedicalReports(Long id);
}
