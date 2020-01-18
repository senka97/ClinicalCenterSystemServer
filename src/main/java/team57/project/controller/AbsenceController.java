package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.AbsenceDTO;
import team57.project.dto.AbsenceRequest;
import team57.project.dto.DoctorDTO;
import team57.project.model.Absence;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.User;
import team57.project.service.ClinicService;
import team57.project.service.DoctorService;
import team57.project.service.impl.AbsenceServiceImpl;
import team57.project.service.impl.DoctorServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/absences")
@CrossOrigin("http://localhost:4200")
public class AbsenceController {

    @Autowired
    private AbsenceServiceImpl absenceService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DoctorServiceImpl doctorService;

    @GetMapping(value="/getAbsence/{id}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAbsence(@PathVariable("id") Long id){

        try {
            Absence absence = absenceService.findOne(id);
            AbsenceDTO absenceDTO = new AbsenceDTO(absence);
            return new ResponseEntity<>(absenceDTO, HttpStatus.OK);

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Absence with that ID don't exist in the system");
        }
    }

    @GetMapping(value="/getAllRequestedAbsences/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAllAbsences(@PathVariable("idClinic") Long idClinic){

        try {
            Clinic clinic = clinicService.findOne(idClinic);
            List<AbsenceDTO> absencesDTO = new ArrayList<AbsenceDTO>();
            for(Absence absence: clinic.getAbsences()){
                if(absence.getStatusOfAbsence().equals("REQUESTED")){
                    absencesDTO.add(new AbsenceDTO(absence));
                }
            }
            return new ResponseEntity<>(absencesDTO, HttpStatus.OK);

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic with that ID don't exist in the system");
        }
    }

    @PostMapping(value="/sendRequestDoctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> sendRequestDoctor(@RequestBody AbsenceRequest absenceRequest){

          if(absenceRequest.getEndDate() == null || absenceRequest.getStartDate() == null ||
          absenceRequest.getTypeOfAbsence().equals("") || absenceRequest.getTypeOfAbsence() == null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to enter a type of absence, start date and end date.");
          }
          Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
          String email = currentUser.getName();
          Doctor doctor = (Doctor) doctorService.findByEmail(email);
          if(absenceService.sendRequestDoctor(doctor, absenceRequest)){
              return ResponseEntity.status(HttpStatus.OK).build();
          }else{
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't be absent in requested period because you have scheduled exams or surgeries.");
          }

    }


}
