package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.MedicalRecord;
import team57.project.model.Patient;
import team57.project.repository.MedicalRecordRepository;
import team57.project.repository.PatientRepository;
import team57.project.service.PatientService;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepostiory;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Override
    public List<Patient> findAll() {
        return patientRepostiory.findAll();
    }

    @Override
    public Patient findOne(Long id) {
        return patientRepostiory.findById(id).orElse(null);
    }

    @Override
    public MedicalRecord findPatientMedicalRecord(Long id) {
        Patient p = patientRepostiory.findById(id).orElse(null);
        Long medicalRecordId = p.getMedicalRecord().getId();
        return medicalRecordRepository.findById(medicalRecordId).orElse(null);
    }


}
