package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.Nurse;
import team57.project.model.Prescription;
import team57.project.repository.PrescriptionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> findUnverified(Long clinic_id) {
        return prescriptionRepository.findUnverified(clinic_id);
    }

    public Prescription findOne(Long id) {
        return prescriptionRepository.findById(id).orElse(null);
    }

    public Prescription save(Prescription p) {
        return prescriptionRepository.save(p);
    }

    @Transactional
    public void verify(Prescription prescription, Nurse nurse)
    {
        prescription.setVerified(true);
        prescription.setNurse(nurse);

        save(prescription);
    }
}