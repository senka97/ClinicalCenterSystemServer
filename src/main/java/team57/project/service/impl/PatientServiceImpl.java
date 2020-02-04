package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.repository.*;
import team57.project.service.ClinicAdminService;
import team57.project.service.ClinicService;
import team57.project.service.EmailService;
import team57.project.service.PatientService;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
    private MedicalReportRepository medicalReportRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private TermDoctorRepository termDoctorRepository;
    @Autowired
    private ExamTypeRepository examTypeRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ClinicAdminService clinicAdminService;
    @Autowired
    private ClinicService clinicService;



    @Override
    public List<Patient> findAll() {
        return patientRepostiory.findAll();
    }

    @Override
    public Patient findOne(Long id) {
        return patientRepostiory.findById(id).orElse(null);
    }

    @Override
    public Patient findOneByEmail(String email) {
        return patientRepostiory.findByEmail(email);
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
    public List<ClinicDTO> leftClinics(Long id) {
        Patient p = this.patientRepostiory.findById(id).orElse(null);
        List<ClinicDTO> leftClin = new ArrayList<ClinicDTO>();
        System.out.println(p);
        if (p != null) {
            List<Long> clinics = this.clinicRepository.patientClinics(id);
            System.out.println(clinics);


            for (Long cl_id : clinics) {
                   Clinic c =  this.clinicRepository.findById(cl_id).orElseGet(null);
                    if(c != null){
                        if (!p.getClinics().contains(c)) {
                            System.out.println(c);

                            leftClin.add(new ClinicDTO(c));
                        }
                    }


            }
        }
        return leftClin;
    }

    @Override
    public List<MedicalReport> getMedicalReports(Long id)
    {
        Patient p = patientRepostiory.findById(id).orElse(null);
        Long medicalRecordId = p.getMedicalRecord().getId();
        return medicalReportRepository.getMedicalReports(medicalRecordId);
    }



    @Override
    public List<UserDTO> findAllInClinic(Authentication currentUser) {
        List<UserDTO> sortedPatients = new ArrayList<UserDTO>();
        String email = currentUser.getName();
        List<Authority> authorities = (List<Authority>) currentUser.getAuthorities();
        Authority authority = authorities.get(0);
        String role = authority.getName();
        if(role.equals("ROLE_DOCTOR")){
            Doctor doctor = doctorRepository.findByEmail(email);
            Set<Patient> patients = doctor.getClinic().getPatients();
            for(Patient patient:patients){
                sortedPatients.add(new UserDTO(patient.getId(),patient.getName(),patient.getSurname(),patient.getEmail(),null,patient.getAddress(),patient.getCity(),patient.getCountry(),patient.getPhoneNumber(),patient.getSerialNumber()));
            }
        }else{
            Nurse nurse = nurseRepository.findByEmail(email);
            Set<Patient> patients = nurse.getClinic().getPatients();
            for(Patient patient:patients){
                sortedPatients.add(new UserDTO(patient.getId(),patient.getName(),patient.getSurname(),patient.getEmail(),null,patient.getAddress(),patient.getCity(),patient.getCountry(),patient.getPhoneNumber(),patient.getSerialNumber()));
            }
        }

        sortedPatients.sort(Comparator.comparing(UserDTO::getName));
        return sortedPatients;
    }

    @Override
    public List<UserDTO> searchPatients(Authentication currentUser, PatientSearch patientSearch) {
        List<UserDTO> sortedPatients = new ArrayList<UserDTO>();
        String email = currentUser.getName();
        Doctor doctor = doctorRepository.findByEmail(email);
        Set<Patient> patients = doctor.getClinic().getPatients();

        for(Patient patient : patients){
            if(!patient.getActivatedAccount().equals("ACCEPTED"))
                continue;
            boolean nameCorrect = true;
            boolean surnameCorrect = true;
            boolean serialNumberCorrect = true;
            if(!patientSearch.getName().equals("") && patientSearch.getName() != null){
                if(patient.getName().toLowerCase().contains(patientSearch.getName().toLowerCase())){
                    nameCorrect = true;
                }else{
                    nameCorrect = false;
                }
            }
            if(!patientSearch.getSurname().equals("") && patientSearch.getSurname() != null){
                if(patient.getSurname().toLowerCase().contains(patientSearch.getSurname().toLowerCase())){
                    surnameCorrect = true;
                }else{
                    surnameCorrect = false;
                }
            }
            if(!patientSearch.getSerialNumber().equals("") && patientSearch.getSerialNumber() != null){

                if(patient.getSerialNumber().startsWith(patientSearch.getSerialNumber())){
                    serialNumberCorrect = true;
                }else{
                    serialNumberCorrect = false;
                }
            }
            if(nameCorrect && surnameCorrect && serialNumberCorrect){
                sortedPatients.add(new UserDTO(patient.getId(),patient.getName(),patient.getSurname(),patient.getEmail(),null,patient.getAddress(),patient.getCity(),patient.getCountry(),patient.getPhoneNumber(),patient.getSerialNumber()));
            }

        }

        sortedPatients.sort(Comparator.comparing(UserDTO::getName));
        return sortedPatients;
    }

    @Override
    public List<String> getAllCities(Authentication currentUser) {
        String email = currentUser.getName();
        Doctor doctor = doctorRepository.findByEmail(email);
        Set<Patient> patients = doctor.getClinic().getPatients();
        List<String> cities = patientRepostiory.getAllCities();
        return cities;

    }

    @Override
    @Transactional
    public MedicalExam sendAppointment(AppointmentDTO appointmentDTO, Long patientId) {
        try{
            Patient p = patientRepostiory.findById(patientId).orElse(null);
            Doctor d = this.doctorRepository.findById(appointmentDTO.getDoctorId()).orElse(null);
            //Lock term
            TermDoctor termDoctor = this.termDoctorRepository.findById(appointmentDTO.getId()).orElseGet(null);

            //Make request for appointment
            MedicalExam examRequest = new MedicalExam(termDoctor);
            ExamType examType = this.examTypeRepository.findByName(appointmentDTO.getType());
            examRequest.setExamType(examType);
            examRequest.setDoctor(d);
            examRequest.setPatient(p);
            examRequest.setExamRoom(null);
            examRequest.setPrice(examType.getPrice());
            examRequest.setDiscount(examType.getDiscount());
            //ovo dodala
            examRequest.setClinic(d.getClinic());

            //Make term occupied
            termDoctor.setFree(false);
            if(p != null){
                p.getMedicalExams().add(examRequest);
            }

            this.medicalExamRepository.save(examRequest);
            this.patientRepostiory.save(p);
            this.termDoctorRepository.save(termDoctor);
            System.out.println("Clinic id : " + d.getClinic().getId() );
            Clinic c = this.clinicRepository.getOne(d.getClinic().getId());

            //ovo dodala
            if(!c.getPatients().contains(p)) {
                c.getPatients().add(p);
                clinicRepository.save(c);
            }

            Set<ClinicAdmin> admins = clinicAdminService.findClinicAdmins(c.getId());
            System.out.println(admins.toString());
            for(ClinicAdmin a : admins){
                System.out.println("Admin" + a.getEmail() + a.getName() + a.getId() );
            }
            //send notification to admin
            this.emailService.notificationAppointmentReq(p,d,examRequest,admins);

        return  examRequest;

        }catch (Exception e){
        return null;
        }



    }


}
