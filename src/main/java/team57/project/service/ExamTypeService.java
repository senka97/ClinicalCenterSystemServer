package team57.project.service;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.dto.ExamTypeDTO;
import team57.project.dto.RoomDTO;
import team57.project.model.Clinic;
import team57.project.model.ExamType;
import team57.project.model.Room;

public interface ExamTypeService {

    ExamType findOne(Long id);
    boolean addExamType(Clinic clinic, ExamTypeDTO examTypeDTO);
    String updateExamType(ExamType examType, ExamTypeDTO examTypeDTO, Clinic clinic);
    boolean removeExamType(ExamType examType, Clinic clinic);
}
