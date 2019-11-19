package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team57.project.dto.UserRequest;
import team57.project.model.Authority;
import team57.project.model.Patient;
import team57.project.model.User;
import team57.project.repository.UserRepository;
import team57.project.service.AuthorityService;
import team57.project.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Override
    public User findById(Long id) throws AccessDeniedException {
        User u = userRepository.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public User findByEmail(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username);
        return u;
    }

    @Override
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User save(UserRequest userRequest) {
        Patient p = new Patient();
        p.setEmail(userRequest.getEmail());
        p.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        p.setName(userRequest.getName());
        p.setSurname(userRequest.getSurname());
        p.setAddress(userRequest.getAddress());
        p.setCity(userRequest.getCity());
        p.setCountry(userRequest.getCountry());
        p.setPhoneNumber(userRequest.getPhoneNumber());
        p.setSerialNumber(userRequest.getSerialNumber());
        p.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_PATIENT");
        p.setAuthorities(auth);

        p = this.userRepository.save(p); //na kraju snimimo korisnika u bazu
        return p;
    }
}
