package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.DiagnosisDTO;
import team57.project.exception.ResourceConflictException;
import team57.project.model.Diagnose;
import team57.project.service.DiagnosisService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/diagnosis")
@CrossOrigin("http://localhost:4200")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping( value = "/getDiagnosis", produces = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> getDiagnosis()
    {
        List<Diagnose> diagnosis = diagnosisService.findAll();

        List<DiagnosisDTO> diagnosisDTO = new ArrayList<>();

        for(Diagnose d : diagnosis)
        {
            diagnosisDTO.add(new DiagnosisDTO(d));
        }

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
    }

    @PutMapping(value="/addNewDiagnosis", produces = "application/json", consumes= "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> addNewDiagnosis(@RequestBody DiagnosisDTO diagnosis)
    {
        Diagnose existDiagnosis = diagnosisService.findByCode(diagnosis.getCode());
        if(existDiagnosis != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Diagnose newDiagnosis= new Diagnose(diagnosis.getCode(),diagnosis.getDescription());
            newDiagnosis = diagnosisService.saveDiagnosis(newDiagnosis);
            return new ResponseEntity<>(new DiagnosisDTO(newDiagnosis),HttpStatus.CREATED);
        }
    }

    @PostMapping(value= "/editDiagnosis", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> editDiagnosis(@RequestBody DiagnosisDTO diagnosis)
    {
        Diagnose existDiagnosis = diagnosisService.findOne(diagnosis.getId());
        if(existDiagnosis == null)
        {
            throw new ResourceConflictException(diagnosis.getId(), "Missing diagnosis");
        }
        else
        {
            Diagnose end = diagnosisService.updateDiagnosis(existDiagnosis, diagnosis);
            if (end != null)
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
