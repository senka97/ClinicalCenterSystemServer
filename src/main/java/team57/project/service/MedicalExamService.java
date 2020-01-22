package team57.project.service;

import team57.project.model.MedicalExam;

import java.util.List;

public interface MedicalExamService {
    List<MedicalExam> findAll();
    List<MedicalExam> findByPatientId(Long patientId);
}