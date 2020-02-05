package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Surgery;

import java.util.List;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

    @Query(value = "SELECT * FROM surgery e WHERE e.patient_id = ?1", nativeQuery = true)
    List<Surgery> findPatientSurgery(Long patientId);

    @Query(value = "SELECT * FROM doctors_surgeries d, surgery s WHERE d.doctor_id=?1 and d.surgery_id= s.id  and s.surgery_room_id is not null "  ,nativeQuery = true)
    List<Surgery> findDoctorsSurgeries(Long doctorId);
}
