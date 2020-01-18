package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
