package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.FastAppointment;
import team57.project.model.Patient;
import team57.project.service.ClinicService;
import team57.project.service.DoctorService;
import team57.project.service.impl.FastAppointmentServiceImpl;
import team57.project.service.impl.PatientServiceImpl;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "api/fastAppointments")
@CrossOrigin("http://localhost:4200")
public class FastAppointmentController {

    @Autowired
    private FastAppointmentServiceImpl fastAppointmentService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private DoctorService doctorService;

    @PostMapping(value = "/addNewFA/{idClinic}", consumes = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> addNewFA(@PathVariable("idClinic") Long idClinic, @RequestBody FARequest faRequest) {
        if (faRequest.getDate() == null || faRequest.getTime() == null || faRequest.getIdDoctor() == null
                || faRequest.getIdExamType() == null || faRequest.getIdRoom() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date, time, room, doctor and exam type are mandatory.");
        }

        try {
            Clinic clinic = clinicService.findOne(idClinic);
            try {
                fastAppointmentService.addNewFA(clinic, faRequest);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (RollbackException re) {
                return ResponseEntity.status(HttpStatus.LOCKED).body("Error occurred in transaction.");
            }

        } catch (NullPointerException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/getAllFA/{id}", produces = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAllFA(@PathVariable("id") Long idClinic) {

        try {
            Clinic clinic = clinicService.findOne(idClinic);
            AllFastAppointments allFAs = fastAppointmentService.getAllFA(clinic);
            return new ResponseEntity(allFAs, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/getFreeFA/{id}", produces = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<?> getFreeFA(@PathVariable("id") Long idClinic) {

        try {
            Clinic clinic = clinicService.findOne(idClinic);
            List<FastAppointmentDTO> freeFastAppointments = fastAppointmentService.getFreeFA(clinic);
            return new ResponseEntity(freeFastAppointments, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/getDoctorPatientFA/{id}", produces = "application/json")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getDoctorPatientFA(@PathVariable("id") Long idPatient) {

        System.out.println("UDJE U FUNKCIJU");
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        Doctor doctor = (Doctor) doctorService.findByEmail(email);

        List<FastAppointment> fa = this.fastAppointmentService.findDoctorPatientFA(doctor.getId(), idPatient);
        List<FastAppointmentPDTO> faDTO = new ArrayList<FastAppointmentPDTO>();

        for(FastAppointment f : fa)
        {
            FastAppointmentPDTO dto = new FastAppointmentPDTO(f);
            faDTO.add(dto);
            //System.out.println(dto.getId());
            //System.out.println(dto.getExamType());
        }
        System.out.println("DUZINA NIZA: " + faDTO.size());

        return new ResponseEntity(faDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getReservedFA/{id}", produces = "application/json")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getReservedFA(@PathVariable("id") Long id) {

        try {
            Doctor doctor= doctorService.findOne(id);
            List<FastAppointmentWKDTO> freeFastAppointments = fastAppointmentService.getReservedFA(doctor);
            return new ResponseEntity(freeFastAppointments, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "reserveFA/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> reserveFA(@PathVariable("id") Long id) {

        try {
            FastAppointment fa = fastAppointmentService.findOne(id);
            Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
            String email = currentUser.getName();
            Patient patient = (Patient) patientService.findOneByEmail(email);

            try {
                String msg = fastAppointmentService.reserveFA(fa, patient);
                if(msg == null) {
                    return ResponseEntity.status(HttpStatus.OK).build();
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
                }
            } catch (OptimisticLockException e1) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Somebody has just reserved this medical exam.");
            }

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
