package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
