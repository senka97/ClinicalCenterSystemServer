package team57.project.service;

import team57.project.dto.UserRequest;
import team57.project.model.User;
import team57.project.model.VerificationToken;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByEmail(String username);
    List<User> findAll ();
    User save(UserRequest userRequest);
    User findByToken(String verificationToken);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);
    void enableRegisteredUser(User user);

    void updateUser(UserRequest userRequest, User user);
}
