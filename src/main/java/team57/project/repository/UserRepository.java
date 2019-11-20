package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
