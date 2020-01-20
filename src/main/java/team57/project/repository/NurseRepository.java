package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Nurse findByEmail(String email);
}
