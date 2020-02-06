package team57.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team57.project.model.Clinic;
import team57.project.model.Nurse;
import team57.project.model.Prescription;
import team57.project.service.ClinicService;
import team57.project.service.NurseService;
import team57.project.service.PrescriptionService;

import javax.persistence.OptimisticLockException;
import java.util.List;

@RestController
@RequestMapping(value = "api/prescriptions")
@CrossOrigin("http://localhost:4200")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private ClinicService clinicService;

    @GetMapping(value = "/getPrescriptions", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_NURSE')")
    public ResponseEntity<?> getPrescriptions() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        Nurse nurse = (Nurse) nurseService.findByEmail(email);

        Clinic clinic = clinicService.findOne(nurse.getClinic().getId());

        List<Prescription> prescriptions = prescriptionService.findUnverified(clinic.getId());

        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PutMapping(value = "/verify/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_NURSE')")
    public ResponseEntity<?> verify(@PathVariable("id") Long id) {
        Prescription prescription = prescriptionService.findOne(id);
        if (prescription == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        Nurse nurse = (Nurse) nurseService.findByEmail(email);

        try{
            prescriptionService.verify(prescription,nurse);
        }
        catch (OptimisticLockException e)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Somebody has just validated this prescription.");
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
}