package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.dto.ClinicDTO;
import team57.project.dto.UserRequest;
import team57.project.exception.ResourceConflictException;
import team57.project.model.Clinic;
import team57.project.model.User;
import team57.project.service.ClinicService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "api/clinics")
@CrossOrigin("http://localhost:4200")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping(value="/getClinics", produces="application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<List<ClinicDTO>> getClinics()
    {
        List<Clinic> clinics = clinicService.findAll();

        List<ClinicDTO> clinicsDTO = new ArrayList<>();
        for(Clinic c : clinics)
        {
            clinicsDTO.add(new ClinicDTO(c));
        }

        return new ResponseEntity<>(clinicsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/editClinic/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> editClinic(@PathVariable("id") Long id, @RequestBody ClinicDTO clinicDTO) {

        Clinic existClinic = clinicService.findOne(id);

        if (existClinic == null) {
            throw new ResourceConflictException(clinicDTO.getId(), "Missing clinic");
        }
        clinicService.updateClinic(existClinic, clinicDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
