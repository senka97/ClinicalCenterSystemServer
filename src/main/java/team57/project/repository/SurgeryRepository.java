package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Surgery;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

    @Query(value = "SELECT * FROM surgery e WHERE e.patient_id = ?1", nativeQuery = true)
    List<Surgery> findPatientSurgery(Long patientId);

    @Query(value = "select s from Clinic c inner join c.surgeries s inner join s.surgeryType st where c.id=?1 and st.id=?2 and (s.date>?3 or (s.date = ?3 and s.endTime>?4))")
    List<Surgery> findSurgeryWithType(Long idClinic, Long id, LocalDate dateNow, LocalTime timeNow);
}
