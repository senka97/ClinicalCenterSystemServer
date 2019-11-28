package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.model.Patient;
import team57.project.model.User;
import team57.project.service.PatientService;

import java.util.Comparator;
import java.util.List;

@RestController
@Controller
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/allSorted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public List<Patient> getAllPatients() {

        List<Patient> sortedPatients = this.patientService.findAll();
        sortedPatients.sort(Comparator.comparing(Patient::getName));
        return sortedPatients;
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public Patient getPatient(@PathVariable("id") Long id) {

        return this.patientService.findOne(id);
    }
}
