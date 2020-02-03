package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.ClinicAdmin;

import java.util.Set;


public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long> {

    ClinicAdmin findByEmail(String email);
    @Query(value = "select DISTINCT a from ClinicAdmin as a where a.clinic.id =?1")
    Set<ClinicAdmin> getClinicAdmins(Long id);

}
