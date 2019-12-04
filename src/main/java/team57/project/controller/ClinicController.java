package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team57.project.dto.ClinicDTO;
import team57.project.model.Clinic;
import team57.project.service.ClinicService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinics")
@CrossOrigin("http://localhost:4200")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping(value="/getClinics", produces="application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
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
}
