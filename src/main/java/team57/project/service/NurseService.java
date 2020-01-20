package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.Nurse;
import team57.project.repository.NurseRepository;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public Nurse findByEmail(String email) {
        return nurseRepository.findByEmail(email);
    }
}
