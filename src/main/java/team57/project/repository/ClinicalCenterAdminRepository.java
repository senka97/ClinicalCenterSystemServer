package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.ClinicalCenterAdmin;

public interface ClinicalCenterAdminRepository extends JpaRepository<ClinicalCenterAdmin, Long> {

    ClinicalCenterAdmin findByEmail(String email);

}
