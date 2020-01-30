package team57.project.service;

import team57.project.dto.MedicalRecordDTO;
import team57.project.model.*;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();
    Patient findOne(Long id);
    MedicalRecord findPatientMedicalRecord(Long id);

    void updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, MedicalRecord record);

    void addAlergicMedication(Medication medication, MedicalRecord record);

    void addChronicCondition(Diagnose diagnose, MedicalRecord record);

    List<Doctor> leftDoctors(Long id);
    Patient save(Patient p);

    List<MedicalReport> getMedicalReports(Long id);

}
