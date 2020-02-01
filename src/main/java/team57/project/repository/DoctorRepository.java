package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team57.project.model.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);
    Doctor findBySerialNumber(String serialNumber);

    @Query(value = "SELECT * FROM User d WHERE d.clinic_id = ?1 AND d.type = 'DOCTOR'", nativeQuery = true)
    List<Doctor> findDoctors(Long idClinic);

   @Query(value = "SELECT * FROM User d WHERE d.clinic_id = ?1 AND d.type = 'DOCTOR'", nativeQuery = true)
   List<Doctor> searchDoctors(Long idClinic,String name, String surname);




}
