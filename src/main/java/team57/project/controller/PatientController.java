package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.service.DiagnosisService;
import team57.project.service.MedicationService;
import team57.project.service.PatientService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@Controller
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private DiagnosisService diagnosisService;

    @RequestMapping(value = "/allSorted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public List<Patient> getAllPatients() {

        List<Patient> sortedPatients = this.patientService.findAll();
        sortedPatients.sort(Comparator.comparing(Patient::getName));
        return sortedPatients;
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
            dto.setTime(mr.getTime());
            DoctorDTO doctorDTO = new DoctorDTO(mr.getDoctor());
            dto.setDoctor(doctorDTO);
            reportsDTO.add(dto);
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }


}
