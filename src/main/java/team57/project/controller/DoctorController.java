package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.*;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.ExamType;
import team57.project.service.ClinicService;
import team57.project.service.ExamTypeService;
import team57.project.service.PatientService;
import team57.project.service.SurgeryTypeService;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.model.SurgeryType;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "api/doctors")
@CrossOrigin("http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ExamTypeService examTypeService;
    @Autowired
    private SurgeryTypeService surgeryTypeService;

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

    @GetMapping(value="/getAllDoctorsRating/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getAllDoctorsRating(@PathVariable("idClinic") Long idClinic){

        try{
            Clinic clinic = clinicService.findOne(idClinic);
            List<DoctorRating> doctorsAll = doctorService.getAllDoctorsRating(idClinic);
            doctorsAll.sort(Comparator.comparingDouble(DoctorRating::getRating).reversed());
            return new ResponseEntity<List<DoctorRating>>(doctorsAll,HttpStatus.OK);
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


    @PutMapping(value = "/rateDoctor/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> rateDoctor(@PathVariable("id") Long id, @RequestBody RateDTO rate) {

        try {
            this.doctorService.rateDoctor(id, rate);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }

    @PostMapping(value = "/getFreeDoctors/{clinicId}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getFreeDoctors(@RequestBody AvailableDoctorRequest adr, @PathVariable("clinicId") Long clinicId) {

        System.out.println("Testiranje" + adr + clinicId);
        if (adr.getIdExamType() == null || adr.getDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exam type and date are mandatory.");
        }
        if (adr.getDate().getDayOfWeek().getValue() == 6 || adr.getDate().getDayOfWeek().getValue() == 7) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't reserve a doctor at the weekend.");
        }
        try {
            Clinic clinic = clinicService.findOne(clinicId);
            List<DoctorRating> freeDoctors = doctorService.findFreeDoctors(clinic, adr);
            return new ResponseEntity(freeDoctors, HttpStatus.OK);

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }
    @PostMapping(value="/getAvailableDoctors/{clinicId}", consumes="application/json", produces = "application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAvailableDoctors(@RequestBody AvailableDoctorRequest adr, @PathVariable("clinicId") Long clinicId){

        if(adr.getIdExamType() == null || adr.getDate() == null || adr.getTime() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exam type, date and time are mandatory.");
        }
        if(adr.getDate().getDayOfWeek().getValue() == 6 || adr.getDate().getDayOfWeek().getValue() == 7){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't reserve a doctor at the weekend.");
        }
        try{
            Clinic clinic = clinicService.findOne(clinicId);
            List<DoctorFA> availableDoctors = doctorService.findAvailableDoctors(clinic,adr);
            return new ResponseEntity(availableDoctors,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.OK).build();
        }


    }
    @PostMapping(value="/getAvailableTerms/{id}", consumes="application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getAvailableTerms(@RequestBody AvailableDoctorRequest adr, @PathVariable("id") Long doctorId){

        System.out.println("Available terms:");
        if(adr.getIdExamType() == null || adr.getDate() == null ){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Exam type and date are mandatory.");
        }
        if(adr.getDate().getDayOfWeek().getValue() == 6 || adr.getDate().getDayOfWeek().getValue() == 7){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("You can't reserve a doctor at the weekend.");
        }
        try{
            List<AppointmentDTO> appointments = this.doctorService.findFreeTerms(doctorId,adr);
            ExamType type = this.examTypeService.findOne(adr.getIdExamType());
            for(AppointmentDTO app : appointments){
                app.setType(type.getName());
            }

            return new ResponseEntity(appointments,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }


    }


    @RequestMapping(value = "/makeSurgeryAppointment/{id}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') ")
    public ResponseEntity<?> makeAppointment(@PathVariable("id") Long patientId, @RequestBody AppointmentDTO appointmentDTO) {
        if(appointmentDTO.getType() == null || appointmentDTO.getDate() == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Surgery type and date are mandatory.");
        }
        if(appointmentDTO.getDate().getDayOfWeek().getValue() == 6 || appointmentDTO.getDate().getDayOfWeek().getValue() == 7){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't reserve surgery at the weekend.");
        }
        try {
            Boolean surgery = this.doctorService.sendSurgeryAppointment(patientId,appointmentDTO);
            if(surgery){
                return new ResponseEntity<Boolean>(surgery, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(surgery, HttpStatus.GONE);
            }
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value="/getDoctorsExamType/{idClinic}/{idExamType}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getDoctorsExamTypes(@PathVariable("idClinic") Long idClinic, @PathVariable("idExamType") Long idExamType) {
        try {
            Clinic clinic = clinicService.findOne(idClinic);
            ExamType examType = examTypeService.findOne(idExamType);
            List<DoctorFA> doctors = doctorService.searchForDoctorsExamTypes(clinic,examType);
            return new ResponseEntity<List<DoctorFA>>(doctors, HttpStatus.OK);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value="/getFreeDoctorsForThisTerm/{id}", produces="application/json", consumes="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getFreeDoctorsForThisTerm(@PathVariable("id") Long clinicId,  @RequestBody RoomTerm rt) {
        try {

            //SurgeryType surgeryType = surgeryTypeService.findOne(rt.getIdSurgeryType());
            //List<Doctor> doctors = doctorService.getDoctorsSurgeryTypes(surgeryType.getId());
            System.out.println(rt.getName());
            List<DoctorFA> doctors = doctorService.getFreeDoctorsForThisTerm(rt, clinicId);
            return new ResponseEntity<List<DoctorFA>>(doctors, HttpStatus.OK);
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
