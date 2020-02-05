package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Absence;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query(value = "SELECT * FROM absence a WHERE (a.doctor_id = ?1 or a.nurse_id=?1) and a.status_of_absence = ?2 "  , nativeQuery = true)
    List<Absence> findAllUserAbsences(Long id, String status);
}
