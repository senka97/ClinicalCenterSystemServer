package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.DoctorDTO;
import team57.project.dto.DoctorSearch;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.service.ClinicService;
import team57.project.service.impl.DoctorServiceImpl;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "api/doctors")
@CrossOrigin("http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private ClinicService clinicService;

    @GetMapping(value="/getDoctor/{id}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getDoctor(@PathVariable("id") Long id){

        try {
            Doctor doctor = doctorService.findOne(id);
            DoctorDTO doctorDTO = new DoctorDTO(doctor);
            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor with that ID don't exist in the system");
        }
    }

    @GetMapping(value="/getAllDoctors/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAllDoctors(@PathVariable("idClinic") Long idClinic){

        try{
            Clinic clinic = clinicService.findOne(idClinic); //kada uradim clinic.getDoctors vraca mi i admine
            List<DoctorSearch> doctorsAll = doctorService.getAllDoctors(idClinic); //napravila svoj upit
            return new ResponseEntity<List<DoctorSearch>>(doctorsAll,HttpStatus.OK);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value="/addDoctor/{idClinic}", consumes = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> addDoctor(@PathVariable("idClinic") Long idClinic, @RequestBody DoctorDTO doctorDTO){

        if(doctorDTO.getName().equals("") || doctorDTO.getName() == null || doctorDTO.getSurname().equals("") || doctorDTO.getSurname() == null ||
        doctorDTO.getAddress().equals("") || doctorDTO.getAddress() == null || doctorDTO.getCity().equals("") || doctorDTO.getCity() == null ||
        doctorDTO.getCountry().equals("") || doctorDTO.getCountry() == null || doctorDTO.getEmail().equals("") || doctorDTO.getEmail() == null ||
        doctorDTO.getPassword().equals("") || doctorDTO.getPassword() == null || doctorDTO.getPhoneNumber().equals("") || doctorDTO.getPhoneNumber() == null ||
                !isSerialNumber(doctorDTO.getSerialNumber()) || doctorDTO.getExamTypesId().size() == 0 || doctorDTO.getSurgeryTypesId().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are mandatory. Serial number must contain 13 numbers. Doctor must have at least one exam type or surgery type.");
        }

        try{
           Clinic clinic = clinicService.findOne(idClinic);
           String msg = doctorService.doctorExists(doctorDTO.getEmail(),doctorDTO.getSerialNumber());
            if(msg == null) {
               doctorService.addDoctor(doctorDTO, clinic);
               return ResponseEntity.status(HttpStatus.OK).build();
           }else{
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
           }
        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping(value="/removeDoctor/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> removeDoctor(@PathVariable("id") Long id){

        try{
              Doctor doctor = doctorService.findOne(id);
              if(doctor.isRemoved()){
                  return ResponseEntity.status(HttpStatus.GONE).build();
              }else{
                  if(doctorService.removeDoctor(doctor)) {
                      return ResponseEntity.status(HttpStatus.OK).build();
                  }else{
                      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This doctor can't be removed because " +
                              "he/she has scheduled medical exams or surgeries in the future.");
                  }
              }

        }catch(NullPointerException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value="/searchDoctors/{clinicId}", consumes="application/json", produces = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> searchDoctors(@RequestBody DoctorSearch doctorSearch, @PathVariable("clinicId") Long clinicId){
        if((doctorSearch.getName() == null || doctorSearch.getName().equals("")) && (doctorSearch.getSurname() == null ||
                doctorSearch.getSurname().equals("")) && (doctorSearch.getSerialNumber() == null || doctorSearch.getSerialNumber().equals(""))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to enter at least one information for search.");
        }

        try {
            Clinic clinic = clinicService.findOne(clinicId);

            List<DoctorSearch> doctors = doctorService.searchForDoctors(doctorSearch, clinicId);
            return new ResponseEntity<List<DoctorSearch>>(doctors, HttpStatus.OK);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }

    private boolean isSerialNumber(String n){
        if (Pattern.matches("[0-9]+", n) && n.length() == 13) {
            return true;
        }else{
            return false;
        }

    }

}
