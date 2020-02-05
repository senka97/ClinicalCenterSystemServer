package team57.project.service;

import team57.project.model.Surgery;

import java.util.List;

public interface SurgeryService {
    List<Surgery> findAll();

    List<Surgery> findByPatientId(Long patientId);
    List<Surgery> findDoctorsSurgeries(Long doctorID);
}
