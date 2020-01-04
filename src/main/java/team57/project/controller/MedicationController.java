package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.MedicationDTO;
import team57.project.exception.ResourceConflictException;
import team57.project.model.Medication;
import team57.project.service.MedicationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/medications")
@CrossOrigin("http://localhost:4200")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping( value = "/getMedications", produces = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> getMedications()
    {
        List<Medication> medications = medicationService.findAll();

        List<MedicationDTO> medicationsDTO = new ArrayList<>();

        for(Medication m : medications)
        {
            medicationsDTO.add(new MedicationDTO(m));
        }

        return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
    }

    @PutMapping(value="/addNewMedication", produces = "application/json", consumes= "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> addNewMedication(@RequestBody MedicationDTO medication)
    {
        Medication existMedication = medicationService.findByCode(medication.getCode());
        if(existMedication != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Medication newMedication= new Medication(medication.getCode(),medication.getDescription());
            newMedication = medicationService.saveMedication(newMedication);
            return new ResponseEntity<>(new MedicationDTO(newMedication),HttpStatus.CREATED);
        }
    }

    @PostMapping(value= "/editMedication", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> editMedication(@RequestBody MedicationDTO medication)
    {
        System.out.println(medication.getId());
        Medication existMedication = medicationService.findOne(medication.getId());
        if(existMedication == null)
        {
            throw new ResourceConflictException(medication.getId(), "Missing medication");
        }
        else
        {
            Medication end = medicationService.updateMedication(existMedication, medication);
            if (end != null)
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

    }
}
