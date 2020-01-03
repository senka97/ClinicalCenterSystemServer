package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Diagnose;

public interface DiagnosisRepository extends JpaRepository<Diagnose, Long> {

    Diagnose findByCode(String code);
}
