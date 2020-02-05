package team57.project.controller;

import org.hibernate.PessimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.Clinic;
import team57.project.model.MedicalExam;
import team57.project.service.ClinicService;
import team57.project.service.MedicalExamService;

import javax.persistence.OptimisticLockException;
import javax.xml.ws.Response;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/medicalExams")
@CrossOrigin("http://localhost:4200")
public class MedicalExamController {
    @Autowired
    private MedicalExamService medicalExamService;
    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value = "/getMedicalExam/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public List<MedicalExamDTO> getMedicalExam(@PathVariable("id") Long id) {
        List<MedicalExam> medicalExams = this.medicalExamService.findByPatientId(id);
        List<MedicalExamDTO> examsDTO = new ArrayList<MedicalExamDTO>();
        System.out.println(medicalExams);
        for(MedicalExam exam : medicalExams){
            if(exam.getExamRoom() != null){
                MedicalExamDTO dto = new MedicalExamDTO();
                dto.setDate(exam.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
                dto.setDoctor(exam.getDoctor().getName());
                dto.setEndTime(exam.getEndTime());
                dto.setStartTime(exam.getStartTime());
                dto.setExamType(exam.getExamType().getName());
                examsDTO.add(dto);
            }

        }
        return examsDTO;
    }

    @PostMapping(value="/getIncome/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getIncome(@PathVariable("id") Long id, @RequestBody IncomeDate incomeDate){

        if(incomeDate.getStartDate() == null || incomeDate.getEndDate() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and end date are mandatory.");
        }

        try{
            Clinic clinic = clinicService.findOne(id);
            double income = medicalExamService.getIncome(clinic,incomeDate);
            return new ResponseEntity(income,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value="/getNumExamRequests/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getNumExamRequests(@PathVariable("id") Long id){

        try{
            Clinic clinic = clinicService.findOne(id);
            double num = medicalExamService.getNumExamRequests(clinic);
            return new ResponseEntity(num,HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value="/getExamRequest/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getExamRequest(@PathVariable("id") Long id){

        try{
            MedicalExam me = medicalExamService.findOne(id);
            MedicalExamRequest mer = new MedicalExamRequest(me);
            return new ResponseEntity(mer,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping(value="/getExamRequests/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getExamRequests(@PathVariable("id") Long id){

        try{
            Clinic clinic = clinicService.findOne(id);
            List<MedicalExamRequest> medicalExamRequests = medicalExamService.findExamRequests(clinic);
            return new ResponseEntity(medicalExamRequests,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value="/getAvailableRoomsExam/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getAvailableRooms(@PathVariable("id") Long id){

        try{
            MedicalExam me = medicalExamService.findOne(id);
            List<RoomME> availableRooms = medicalExamService.getAvailableRooms(me);
            return new ResponseEntity(availableRooms,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value="/reserveRoom", consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> reserveRoom(@RequestBody MERoomRequest meRoomRequest){

        try{
                medicalExamService.reserveRoom(meRoomRequest);
                return ResponseEntity.status(HttpStatus.OK).build();

            }catch(PessimisticLockException pe){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This room has just been reserved for that term.");
            }catch (OptimisticLockException oe){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Some other admin has just found the room for this medical exam.");
            }

    }
}
