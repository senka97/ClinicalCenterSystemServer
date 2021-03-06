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
import team57.project.dto.AbsenceWKDTO;
import team57.project.dto.DoctorDTO;
import team57.project.model.*;
import team57.project.service.ClinicService;
import team57.project.service.DoctorService;
import team57.project.service.NurseService;
import team57.project.service.UserService;
import team57.project.service.impl.AbsenceServiceImpl;
import team57.project.service.impl.DoctorServiceImpl;

import javax.xml.ws.Response;
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
    @Autowired
    private NurseService nurseService;
    @Autowired
    private UserService userService;

    @GetMapping(value="/getAbsence/{id}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAbsence(@PathVariable("id") Long id){

        try {
            Absence absence = absenceService.findOne(id);
            AbsenceDTO absenceDTO = new AbsenceDTO(absence);
            return new ResponseEntity<>(absenceDTO, HttpStatus.OK);

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Absence with that ID doesn't exist in the system");
        }
    }

    @GetMapping(value="/getNumberOfRequests/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getNumberOfRequests(@PathVariable("idClinic") Long idClinic) {

        try{
            Clinic clinic = clinicService.findOne(idClinic);
            int num = 0;
            for(Absence absence: clinic.getAbsences()){
                if(absence.getStatusOfAbsence().equals("REQUESTED")){
                    num++;
                }
            }
            return new ResponseEntity(num, HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic with that ID doesn't exist in the system");
        }
    }

    @PostMapping(value="/sendRequestDoctor", consumes="application/json")
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

    @PostMapping(value="/sendRequestNurse")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<?> sendRequestNurse(@RequestBody AbsenceRequest absenceRequest){

        if(absenceRequest.getEndDate() == null || absenceRequest.getStartDate() == null ||
                absenceRequest.getTypeOfAbsence().equals("") || absenceRequest.getTypeOfAbsence() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to enter a type of absence, start date and end date.");
        }
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        Nurse nurse = (Nurse) nurseService.findByEmail(email);
        if(absenceService.sendRequestNurse(nurse, absenceRequest)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't be absent in requested period."); //because
        }

    }

    @PutMapping(value="/approveAbsence/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> approveAbsence(@PathVariable("id") Long id){

        try{
            Absence absence = absenceService.findOne(id);
            if(!absence.getStatusOfAbsence().equals("REQUESTED")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This absence request has already been completed.");
            }
            String msg = absenceService.approveAbsence(absence);
            if(msg == null){
                return ResponseEntity.status(HttpStatus.OK).build();

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value="/rejectAbsence/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> rejectAbsence(@PathVariable("id") Long id, @RequestBody String message){

        try{
            Absence absence = absenceService.findOne(id);
            if(!absence.getStatusOfAbsence().equals("REQUESTED")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This absence request has already been completed.");
            }
            if(absenceService.rejectAbsence(absence, message)){
                return ResponseEntity.status(HttpStatus.OK).build();

            }else{
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong with sending email notification. Please try again.");
            }

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping(value="/getAllAbsences/{id}", produces="application/json")
    @PreAuthorize("hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAllApprovedAbsences(@PathVariable("id") Long id){

        try {
            User u = userService.findById(id);
            List<Absence> absences = absenceService.findAllUserAbsences(id);
            List<AbsenceWKDTO> absencesDTO = new ArrayList<AbsenceWKDTO>();
           for(Absence absence: absences){
                AbsenceWKDTO dto = new AbsenceWKDTO(absence);
                absencesDTO.add(dto);
            }

            return new ResponseEntity<>(absencesDTO, HttpStatus.OK);

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic with that ID doesn't exist in the system");
        }
    }

}
