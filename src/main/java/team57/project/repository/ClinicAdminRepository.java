package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.ClinicAdmin;


public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long> {

    ClinicAdmin findByEmail(String email);
}
