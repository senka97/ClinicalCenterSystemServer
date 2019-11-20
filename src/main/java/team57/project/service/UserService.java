package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.ClinicAdmin;
import team57.project.model.ClinicalCenterAdmin;
import team57.project.model.User;
import team57.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ClinicAdmin saveClinicAdmin(ClinicAdmin user) {
        return userRepository.save(user);
    }

    public ClinicalCenterAdmin saveClinicalCenterAdmin(ClinicalCenterAdmin user) {
        return userRepository.save(user);
    }

    /*public ClinicAdmin findOne(Long id) {
        return userRepository.findById(id).orElseGet(null);
    }*/
}
