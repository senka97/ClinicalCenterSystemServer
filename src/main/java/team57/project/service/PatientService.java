package team57.project.service;

import team57.project.model.MedicalRecord;
import team57.project.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();
    Patient findOne(Long id);
    MedicalRecord findPatientMedicalRecord(Long id);
    Patient save(Patient p);

}
