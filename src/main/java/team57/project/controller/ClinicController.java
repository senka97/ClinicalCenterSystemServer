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
import team57.project.dto.*;
import team57.project.exception.ResourceConflictException;
import team57.project.model.Clinic;
import team57.project.model.Room;
import team57.project.model.User;
import team57.project.service.ClinicService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
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

    @GetMapping(value="/getClinic/{id}", produces="application/json")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getClinic(@PathVariable("id") Long id)
    {
         try{
             Clinic clinic = clinicService.findOne(id);
             ClinicDTO clinicDTO = new ClinicDTO(clinic);
             return new ResponseEntity<>(clinicDTO, HttpStatus.OK);

         }catch(NullPointerException e){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }


    }


    @RequestMapping(value = "/editClinic/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> editClinic(@PathVariable("id") Long id, @RequestBody ClinicDTO clinicDTO) {

        if(clinicDTO.getAddress() == null || clinicDTO.getAddress().equals("") || clinicDTO.getDescription() == null
        || clinicDTO.getDescription().equals("") || clinicDTO.getName() == null || clinicDTO.getName().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Clinic's name, description and address are mandatory.");

        }
        try {
            Clinic existClinic = clinicService.findOne(id);
            boolean nameExists = clinicService.clinicNameExists(clinicDTO.getName(),id);
            if(!nameExists){
                clinicService.updateClinic(existClinic, clinicDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Clinic with that name already exists.");
            }
        } catch(NullPointerException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(value="/getRooms/{id}", produces="application/json")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity getRooms(@PathVariable("id") Long id)
    {
        try {
            Clinic existClinic = clinicService.findOne(id);
            Set<Room> rooms = existClinic.getRooms();

            List<RoomDTO> roomsDTO = new ArrayList<>();
            for(Room r : rooms)
            {
                if(!r.isRemoved()) {
                    roomsDTO.add(new RoomDTO(r));
                }
            }

            return new ResponseEntity(roomsDTO, HttpStatus.OK);

        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }



    }

    @RequestMapping(value = "/addNewRoom/{id}", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> editClinic(@PathVariable("id") Long id, @RequestBody RoomDTO roomDTO) {

        try {
            Clinic existClinic = clinicService.findOne(id);
            String msg = "";
            boolean error = false;
            Long idRoom;
            Set<Room> roomsInClinic = existClinic.getRooms();
            for(Room room: roomsInClinic){
                if(room.getName().equals(roomDTO.getName()) && !room.isRemoved()){
                    msg += "A room with that name already exists in this clinic. ";
                    error = true;
                }
                if(room.getNumber().equals(roomDTO.getNumber()) && !room.isRemoved()){
                    msg += "A room with that number already exists in this clinic.";
                    error = true;
                }
            }

            if (error) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }
            clinicService.addNewRoom(existClinic, roomDTO);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch(NullPointerException e) {

            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping(value = "/rateClinic/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> rateClinic(@PathVariable("id") Long id, @RequestBody RateDTO rate) {

        try {
            this.clinicService.rateClinic(id, rate);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }
    @PutMapping(value = "/getFreeClinics", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getFreeClinics(@RequestBody AvailableDoctorRequest adr) {

        System.out.println("Testiranje" + adr);
        System.out.println("Testiranje" + adr.getIdExamType());
        System.out.println("Testiranje" + adr.getDate());
        if (adr.getIdExamType() == null || adr.getDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exam type and date are mandatory.");
        }
        if (adr.getDate().getDayOfWeek().getValue() == 6 || adr.getDate().getDayOfWeek().getValue() == 7) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't reserve a doctor at the weekend.");
        }
        try {

            List<ClinicDTO> freeClinics = this.clinicService.findFreeClinics(adr);
            return new ResponseEntity(freeClinics, HttpStatus.OK);

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping(value="/getIncome/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getIncome(@PathVariable("id") Long id, @RequestBody IncomeDate incomeDate){

        if(incomeDate.getStartDate() == null || incomeDate.getEndDate() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and end date are mandatory.");
        }

        try{
            Clinic clinic = clinicService.findOne(id);
            double income = clinicService.getIncome(clinic,incomeDate);
            return new ResponseEntity(income,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping(value="/getDailyReport/{idClinic}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getDailyReport(@PathVariable("idClinic") Long id,@RequestBody LocalDate date){

        try{
            Clinic clinic = clinicService.findOne(id);
            List<Hour> hours = clinicService.getDailyReport(clinic,date);
            return new ResponseEntity(hours,HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value="/getMonthlyReport/{idClinic}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getMonthlyReport(@PathVariable("idClinic") Long id,@RequestBody LocalDate date){

        try{
            Clinic clinic = clinicService.findOne(id);
            List<Week> weeks = clinicService.getMonthlyReport(clinic,date);
            return new ResponseEntity(weeks,HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value="/getAnnualReport/{idClinic}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getAnnualReport(@PathVariable("idClinic") Long id,@RequestBody LocalDate date){

        try{
            Clinic clinic = clinicService.findOne(id);
            List<Month> months = clinicService.getAnnualReport(clinic,date);
            return new ResponseEntity(months,HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
