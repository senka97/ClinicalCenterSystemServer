package team57.project.service;

import team57.project.dto.MedicalRecordDTO;
import team57.project.model.Diagnose;
import team57.project.model.MedicalRecord;
import team57.project.model.Medication;
import team57.project.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();
    Patient findOne(Long id);
    MedicalRecord findPatientMedicalRecord(Long id);

    void updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, MedicalRecord record);

    void addAlergicMedication(Medication medication, MedicalRecord record);

    void addChronicCondition(Diagnose diagnose, MedicalRecord record);

    Patient save(Patient p);

}
