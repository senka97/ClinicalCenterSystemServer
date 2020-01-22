package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.MedicalExam;
import team57.project.repository.MedicalExamRepository;
import team57.project.service.MedicalExamService;

import java.util.List;
@Service
public class MedicalExamServiceImpl implements MedicalExamService {
    @Autowired
    private MedicalExamRepository medicalExamRepository;

    @Override
    public List<MedicalExam> findAll() {
        return this.medicalExamRepository.findAll();
    }

    @Override
    public List<MedicalExam> findByPatientId(Long patientId) {
        return this.medicalExamRepository.findMedicalExamByPatient(patientId);
    }

}
