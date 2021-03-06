package team57.project.service;

import org.springframework.security.core.Authentication;
import team57.project.dto.*;
import team57.project.model.*;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();
    Patient findOne(Long id);
    Patient findOneByEmail(String email);
    MedicalRecord findPatientMedicalRecord(Long id);

    void updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, MedicalRecord record);

    void addAlergicMedication(Medication medication, MedicalRecord record);

    void addChronicCondition(Diagnose diagnose, MedicalRecord record);

    List<Doctor> leftDoctors(Long id);
    List<ClinicDTO> leftClinics(Long id);
    Patient save(Patient p);

    List<MedicalReport> getMedicalReports(Long id);

    List<UserDTO> findAllInClinic(Authentication currentUser);
    List<UserDTO> searchPatients(Authentication currentUser, PatientSearch patientSearch);
    List<String> getAllCities(Authentication currentUser);


    Boolean sendAppointment(AppointmentDTO appointmentDTO, Long patientId);
}
