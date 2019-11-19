package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
