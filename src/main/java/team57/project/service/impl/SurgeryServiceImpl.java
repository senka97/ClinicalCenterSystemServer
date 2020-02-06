package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.Surgery;
import team57.project.repository.SurgeryRepository;
import team57.project.service.SurgeryService;

import java.util.List;

@Service
public class SurgeryServiceImpl implements SurgeryService {
    @Autowired
    private SurgeryRepository surgeryRepository;

    @Override
    public List<Surgery> findAll() {
        return this.surgeryRepository.findAll();
    }

    @Override
    public List<Surgery> findByPatientId(Long patientId) {
        return this.surgeryRepository.findPatientSurgery(patientId);
    }

    @Override
    public List<Surgery> findDoctorsSurgeries(Long doctorID){
        return this.surgeryRepository.findDoctorsSurgeries(doctorID);
    }

    @Override
    public void save(Surgery s) {
        this.surgeryRepository.save(s);
    }
}
