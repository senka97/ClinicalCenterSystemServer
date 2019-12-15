package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Clinic;

public interface ClinicRepository  extends JpaRepository<Clinic, Long> {

     Clinic findByName(String name);
}
