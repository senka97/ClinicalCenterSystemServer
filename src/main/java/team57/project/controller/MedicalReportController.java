package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.service.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "api/medicalReport")
@CrossOrigin("http://localhost:4200")
public class MedicalReportController {

    @Autowired
    private MedicalReportService medicalReportService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "/getPrescriptions/{id}", produces ="application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public Set<PrescriptionDTO> getMedicalReportPrescriptions(@PathVariable("id") Long id) {
        Set<Prescription> prescriptions = this.medicalReportService.findOne(id).getPrescriptions();
        Set<PrescriptionDTO> prescDTO =new HashSet<PrescriptionDTO>();
        for(Prescription p : prescriptions)
        {
            PrescriptionDTO dto = new PrescriptionDTO();
            dto.setId(p.getId());
            dto.setVerified(p.getVerified());
            dto.setMedication(p.getMedication());
            DoctorDTO doctor = new DoctorDTO(p.getDoctor());
            dto.setDoctor(doctor);
            if(p.getNurse() != null)
            {
                NurseDTO nurse = new NurseDTO(p.getNurse());
                dto.setNurse(nurse);
            }
            else dto.setNurse(null);
            prescDTO.add(dto);
        }
        return prescDTO;
    }

    @GetMapping(value = "/getDiagnosis/{id}", produces ="application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public Set<Diagnose> getMedicalReportDiagnosis(@PathVariable("id") Long id) {
        return this.medicalReportService.findOne(id).getDiagnoses();
    }

    @PostMapping(value = "/addDiagnosis/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> addDiagnosis(@PathVariable("id") Long id, @RequestBody DiagnosisDTO diagnose) {

        MedicalReport report= this.medicalReportService.findOne(id);

        AtomicBoolean add = new AtomicBoolean(false);
        report.getDiagnoses().forEach(m -> {

            if (m.getId().equals(diagnose.getId())) {
                add.set(true);
            }

        });
        if (!(add.get())) {
            this.medicalReportService.addDiagnosis(this.diagnosisService.findOne(diagnose.getId()),report);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

    }

    @PostMapping(value = "/createMedicalReport/{id}", produces ="application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> createMedicalReport(@PathVariable("id") Long id, @RequestBody MedicalReportDTO medicalReport) throws ParseException {

        MedicalReport newReport = new MedicalReport();
        Doctor doctor = doctorService.findOne(medicalReport.getDoctor().getId());
        Patient patient = patientService.findOne(id);
        Clinic clinic = clinicService.findOne(doctor.getClinic().getId());
        MedicalRecord record = patientService.findPatientMedicalRecord(id);

        newReport.setDoctor(doctor);
        newReport.setDescription(medicalReport.getDescription());
        newReport.setTime(medicalReport.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        java.util.Date date = formatter.parse(medicalReport.getDate());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        newReport.setDate(sqlDate);

        if(medicalReport.getMedications() != null)
        {
            System.out.println("Usao u medications");
            for(Medication m : medicalReport.getMedications())
            {
                System.out.println("usao u for");
                Prescription p = new Prescription();
                Medication med = medicationService.findOne(m.getId());
                p.setVerified(false);
                p.setDoctor(doctor);
                p.setNurse(null);
                p.setPatient(patient);
                p.setMedication(med);
                p.setClinic(clinic);
                newReport.getPrescriptions().add(p);
                prescriptionService.save(p);
                System.out.println("zavrsio for");
            }
        }

        if(medicalReport.getDiagnoses() != null)
        {
            System.out.println("Usao u diagnoses");
            for(Diagnose d : medicalReport.getDiagnoses())
            {
                System.out.println("usao u for");
                Diagnose diag = diagnosisService.findOne(d.getId());
                newReport.getDiagnoses().add(diag);
                System.out.println("zavrsio for");
            }
        }

        record.getMedicalReports().add(newReport);

        medicalReportService.save(newReport);
        medicalRecordService.save(record);

        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteDiagnosis/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteDiagnosis(@PathVariable("id") Long id, @RequestBody DiagnosisDTO diagnose) {
        MedicalReport report= this.medicalReportService.findOne(id);
        this.medicalReportService.deleteDiagnosisFromReport(this.diagnosisService.findOne(diagnose.getId()),report);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/editMedicalReport", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> editMedicalReport(@RequestBody MedicalReportDTO report)
    {
        MedicalReport medicalReport = medicalReportService.findOne(report.getId());
        medicalReport.setDescription(report.getDescription());
        medicalReportService.save(medicalReport);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
