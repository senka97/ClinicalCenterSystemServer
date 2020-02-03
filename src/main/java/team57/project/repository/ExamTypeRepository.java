package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.ExamType;

public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {
    ExamType findByName(String name);
}
