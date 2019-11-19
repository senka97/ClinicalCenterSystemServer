package team57.project.service;

import team57.project.dto.UserRequest;
import team57.project.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByEmail(String username);
    List<User> findAll ();
    User save(UserRequest userRequest);
}
