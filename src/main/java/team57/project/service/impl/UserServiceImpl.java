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
import team57.project.model.VerificationToken;
import team57.project.repository.UserRepository;
import team57.project.repository.VerificationTokenRepository;
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
    @Autowired
    private VerificationTokenRepository tokenRepository;

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
        p.setEnabled(false);
        List<Authority> auth = authService.findByname("ROLE_PATIENT");
        p.setAuthorities(auth);

        p = this.userRepository.save(p); //na kraju snimimo korisnika u bazu
        return p;
    }



    @Override
    public User findByToken(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String berificationToken) {
        return tokenRepository.findByToken(berificationToken);
    }

    @Override
    public void enableRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserRequest userRequest, User existUser) {
        existUser.setName(userRequest.getName());
        existUser.setSurname(userRequest.getSurname());
        existUser.setPhoneNumber(userRequest.getPhoneNumber());
        existUser.setCountry(userRequest.getCountry());
        existUser.setCity(userRequest.getCity());
        existUser.setAddress(userRequest.getAddress());
        userRepository.save(existUser);


    }

}
