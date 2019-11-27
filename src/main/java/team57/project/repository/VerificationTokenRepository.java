package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.User;
import team57.project.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

        VerificationToken findByToken(String token);
        VerificationToken findByUser(User user);
}