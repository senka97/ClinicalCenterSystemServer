package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.ClinicalCenterAdmin;
import team57.project.model.User;

import java.util.List;

public interface ClinicalCenterAdminRepository extends JpaRepository<ClinicalCenterAdmin, Long> {

    @Query("select u from User u where u.activatedAccount = ?1")
    List<User> findNewRequests(String f);
}
