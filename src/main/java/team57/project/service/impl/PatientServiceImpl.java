package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.MedicalRecordDTO;
import team57.project.model.*;
import team57.project.repository.DoctorRepository;
import team57.project.repository.MedicalExamRepository;
import team57.project.repository.MedicalRecordRepository;
import team57.project.repository.PatientRepository;
import team57.project.service.PatientService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepostiory;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private MedicalExamRepository medicalExamRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Patient> findAll() {
        return patientRepostiory.findAll();
    }

    @Override
    public Patient findOne(Long id) {
        return patientRepostiory.findById(id).orElse(null);
    }

    @Override
    public Patient save(Patient p) {
        return patientRepostiory.save(p);
    }

    @Override
    public MedicalRecord findPatientMedicalRecord(Long id) {
        Patient p = patientRepostiory.findById(id).orElse(null);
        Long medicalRecordId = p.getMedicalRecord().getId();
        return medicalRecordRepository.findById(medicalRecordId).orElse(null);
    }

    @Override
    public void updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, MedicalRecord record) {
        if (record != null) {
            record.setHeight(medicalRecordDTO.getHeight());
            record.setWeight(medicalRecordDTO.getWeight());
            record.setOrganDonor(medicalRecordDTO.isOrganDonor());
            record.setBloodType(medicalRecordDTO.getBloodType());
            record.setDiopter(medicalRecordDTO.getDiopter());
//                record.setChronicConditions(
//                record.setAllergicToMedications();
            this.medicalRecordRepository.save(record);

        }
    }

    @Override
    public void addAlergicMedication(Medication medication, MedicalRecord record) {
        if (record != null) {
            record.getAllergicToMedications().add(medication);
            this.medicalRecordRepository.save(record);
        }
    }

    @Override
    public void addChronicCondition(Diagnose diagnose, MedicalRecord record) {
        if (record != null) {
            record.getChronicConditions().add(diagnose);
            this.medicalRecordRepository.save(record);
        }
    }

    @Override
    public List<Doctor> leftDoctors(Long id) {

        //Razmisliti o transakc
        Patient p = this.patientRepostiory.findById(id).orElse(null);
        List<Doctor> leftDoct = new ArrayList<Doctor>();
        if (p != null) {
            List<Long> doctors = this.medicalExamRepository.findDoctors(id);

            for (Long docId : doctors) {
                Doctor d = this.doctorRepository.findById(docId).orElse(null);
                if (d != null) {
                    if (!p.getDoctors().contains(d)) {
                        leftDoct.add(d);
                    }
                }
            }
        }
        return leftDoct;
    }
}
