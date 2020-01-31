package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.MedicalRecordDTO;
import team57.project.model.*;
import team57.project.repository.*;
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
    @Autowired
    private ClinicRepository clinicRepository;

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
            record.setDateOfBirth(medicalRecordDTO.getDateOfBirth());
            record.setHeight(medicalRecordDTO.getHeight());
            record.setWeight(medicalRecordDTO.getWeight());
            record.setOrganDonor(medicalRecordDTO.getOrganDonor());
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

    @Override
    public List<Clinic> leftClinics(Long id) {
        Patient p = this.patientRepostiory.findById(id).orElse(null);
        List<Clinic> leftClin = new ArrayList<Clinic>();
        System.out.println(p);
        if (p != null) {
            List<Long> clinics = this.clinicRepository.patientClinics(id);
            System.out.println(clinics);


            for (Long cl_id : clinics) {
                   Clinic c =  this.clinicRepository.findById(cl_id).orElseGet(null);
                    if(c != null){
                        if (!p.getClinics().contains(c)) {
                            System.out.println(c);

                            leftClin.add(c);
                        }
                    }


            }
        }
        return leftClin;
    }
}
