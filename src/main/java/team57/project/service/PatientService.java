package team57.project.service;

import org.springframework.security.core.Authentication;
import team57.project.dto.AppointmentDTO;
import team57.project.dto.MedicalRecordDTO;
import team57.project.dto.PatientSearch;
import team57.project.dto.UserDTO;
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
    List<Clinic> leftClinics(Long id);
    Patient save(Patient p);

    List<UserDTO> findAllInClinic(Authentication currentUser);
    List<UserDTO> searchPatients(Authentication currentUser, PatientSearch patientSearch);
    List<String> getAllCities(Authentication currentUser);


    MedicalExam sendAppointment(AppointmentDTO appointmentDTO, Long patientId);
}
