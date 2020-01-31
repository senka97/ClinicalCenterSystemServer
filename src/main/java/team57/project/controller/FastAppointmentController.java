package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.AllFastAppointments;
import team57.project.dto.FARequest;
import team57.project.dto.FastAppointmentDTO;
import team57.project.model.Clinic;
import team57.project.service.ClinicService;
import team57.project.service.impl.FastAppointmentServiceImpl;

import javax.persistence.RollbackException;
import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping(value = "api/fastAppointments")
@CrossOrigin("http://localhost:4200")
public class FastAppointmentController {

    @Autowired
    private FastAppointmentServiceImpl fastAppointmentService;
    @Autowired
    private ClinicService clinicService;

    @PostMapping(value="/addNewFA/{idClinic}", consumes = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> addNewFA(@PathVariable("idClinic") Long idClinic, @RequestBody FARequest faRequest){
        if(faRequest.getDate()==null || faRequest.getTime()==null || faRequest.getIdDoctor()==null
        || faRequest.getIdExamType()==null || faRequest.getIdRoom()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date, time, room, doctor and exam type are mandatory.");
        }

        try{
            Clinic clinic = clinicService.findOne(idClinic);
            try {
                fastAppointmentService.addNewFA(clinic, faRequest);
                return ResponseEntity.status(HttpStatus.OK).build();
            }catch (RollbackException re){
                return ResponseEntity.status(HttpStatus.LOCKED).body("Error occurred in transaction.");
            }

        }catch (NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value="/getAllFA/{id}", produces="application/json")
    public ResponseEntity<?> getAllFA(@PathVariable("id") Long idClinic){

        try{
            Clinic clinic = clinicService.findOne(idClinic);
            AllFastAppointments allFAs = fastAppointmentService.getAllFA(clinic);
            return new ResponseEntity(allFAs,HttpStatus.OK);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value="/getFreeFA/{id}", produces = "application/json")
    public ResponseEntity<?> getFreeFA(@PathVariable("id") Long idClinic){

        try {
            Clinic clinic = clinicService.findOne(idClinic);
            List<FastAppointmentDTO> freeFastAppointments = fastAppointmentService.getFreeFA(clinic);
            return new ResponseEntity(freeFastAppointments,HttpStatus.OK);
        } catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
