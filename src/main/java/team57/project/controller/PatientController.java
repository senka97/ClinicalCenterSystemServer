package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.service.DiagnosisService;
import team57.project.service.MedicalRecordService;
import team57.project.service.MedicationService;
import team57.project.service.NurseService;
import team57.project.service.PatientService;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.service.impl.PatientServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@Controller
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    @Autowired //OVO SAM promjenio
    private PatientServiceImpl patientService;
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private NurseService nurseService;

    @RequestMapping(value = "/allSorted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public ResponseEntity<?> getAllPatients() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        List<UserDTO> sortedPatients =  patientService.findAllInClinic(currentUser);
        return new ResponseEntity(sortedPatients,HttpStatus.OK);
    }

    @RequestMapping(value = "/searchPatients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> searchPatients(@RequestBody PatientSearch patientSearch) {

        if((patientSearch.getName().equals("") || patientSearch.getName()==null) && (patientSearch.getSurname().equals("") || patientSearch.getSurname() == null) && (
                patientSearch.getSerialNumber().equals("") || patientSearch.getSerialNumber() == null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to enter at least one valid information for search.");
        }
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        List<UserDTO> searchPatients = patientService.searchPatients(currentUser,patientSearch);
        return new ResponseEntity(searchPatients,HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllCities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getAllCities() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        List<String> cities = patientService.getAllCities(currentUser);
        return new ResponseEntity(cities,HttpStatus.OK);
    }




    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public Patient getPatient(@PathVariable("id") Long id) {

        return this.patientService.findOne(id);
    }

    @RequestMapping(value = "/patientMedicalRecord/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT') or hasRole('ROLE_NURSE')")
    public MedicalRecord getPatientMedicalRecord(@PathVariable("id") Long id) {

        return this.patientService.findPatientMedicalRecord(id);
    }

    @RequestMapping(value = "/editMedicalRecord/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public ResponseEntity<?> editMedicalRecord(@PathVariable("id") Long id, @RequestBody MedicalRecordDTO medicalRecordDTO) {

        MedicalRecord record = this.patientService.findPatientMedicalRecord(id);

        this.patientService.updateMedicalRecord(medicalRecordDTO, record);
        return new ResponseEntity<MedicalRecord>(record, HttpStatus.OK);
    }

    //Mozda ovdje iskoristiti!
    @RequestMapping(value = "/addAlergicMedication/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public ResponseEntity<?> addAlergicMedication(@PathVariable("id") Long id, @RequestBody MedicationDTO medication) {

        MedicalRecord record = this.patientService.findPatientMedicalRecord(id);

        AtomicBoolean add = new AtomicBoolean(false);
        record.getAllergicToMedications().forEach(m -> {

            if (m.getId().equals(medication.getId())) {
                add.set(true);
            }

        });
        if (!(add.get())) {
            this.patientService.addAlergicMedication(this.medicationService.findOne(medication.getId()), record);
        }
        return new ResponseEntity<MedicalRecord>(record, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPatientAlergicMed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public Set<Medication> getPatientAlergicMed(@PathVariable("id") Long id) {
        return this.patientService.findOne(id).getMedicalRecord().getAllergicToMedications();
    }

    @RequestMapping(value = "/addChronicCondition/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public ResponseEntity<?> addChronicCondition(@PathVariable("id") Long id, @RequestBody DiagnosisDTO diagnose) {

        MedicalRecord record = this.patientService.findPatientMedicalRecord(id);

        AtomicBoolean add = new AtomicBoolean(false);
        record.getChronicConditions().forEach(m -> {

            if (m.getId().equals(diagnose.getId())) {
                add.set(true);
            }

        });
        if (!(add.get())) {
            this.patientService.addChronicCondition(this.diagnosisService.findOne(diagnose.getId()), record);
        }
        return new ResponseEntity<MedicalRecord>(record, HttpStatus.OK);
    }
    @RequestMapping(value = "/makeAppointment/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> makeAppointment(@PathVariable("id") Long id, @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Boolean exam = this.patientService.sendAppointment(appointmentDTO,id);

            System.out.println(id +" " +appointmentDTO.getType() + " " + appointmentDTO.getDate()
            + "  " + appointmentDTO.getTime() + "  " + exam
            );
            if(exam){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.GONE);
            }
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/getPatientChronicCon/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public Set<Diagnose> getPatientChronicCon(@PathVariable("id") Long id) {
        return this.patientService.findOne(id).getMedicalRecord().getChronicConditions();
    }

    @RequestMapping(value = "/getRatedDoctors/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public List<Doctor> getRatedDoctors(@PathVariable("id") Long id) {

        return this.patientService.leftDoctors(id);
    }
    @RequestMapping(value = "/getRatedClinics/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public List<ClinicDTO> getRatedClinics(@PathVariable("id") Long id) {

        return this.patientService.leftClinics(id);
    }

    private boolean isSerialNumber(String n) {
        if (Pattern.matches("[0-9]+", n) && n.length() == 13) {
            return true;
        } else {
            return false;
        }
    }
    @GetMapping(value = "/getMedicalReports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getMedicalReports(@PathVariable("id") Long id) //patient id
    {
        List<MedicalReport> reports=patientService.getMedicalReports(id);
        List<MedicalReportDTO> reportsDTO = new ArrayList<MedicalReportDTO>();

        for(MedicalReport mr : reports)
        {
            MedicalReportDTO dto = new MedicalReportDTO();
            dto.setId(mr.getId());
            dto.setDescription(mr.getDescription());
            dto.setDate(mr.getDate().toLocalDate().toString());
            dto.setTime(mr.getTime().toString());
            DoctorDTO doctorDTO = new DoctorDTO(mr.getDoctor());
            dto.setDoctor(doctorDTO);
            reportsDTO.add(dto);
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteChronicCondition/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteDiagnosis(@PathVariable("id") Long id, @RequestBody DiagnosisDTO diagnose) {
        MedicalRecord record = this.patientService.findPatientMedicalRecord(id);
        this.medicalRecordService.deleteChronicCondition(this.diagnosisService.findOne(diagnose.getId()),record);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAllergicMedication/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteAllergicMedication(@PathVariable("id") Long id, @RequestBody MedicationDTO medication) {
        MedicalRecord record = this.patientService.findPatientMedicalRecord(id);
        this.medicalRecordService.deleteAllergicMedication(this.medicationService.findOne(medication.getId()),record);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
