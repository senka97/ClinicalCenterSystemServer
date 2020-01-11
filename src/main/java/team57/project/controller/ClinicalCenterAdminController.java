package team57.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.dto.ClinicAdminDTO;
import team57.project.dto.ClinicDTO;
import team57.project.dto.ClinicalCenterAdminDTO;
import team57.project.dto.UserDTO;
import team57.project.event.OnRegistrationSuccessEvent;
import team57.project.exception.ResourceConflictException;
import team57.project.model.*;
import team57.project.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "api/clinicalCenterAdmin")
@CrossOrigin("http://localhost:4200")
public class ClinicalCenterAdminController {

    @Autowired
    private ClinicalCenterAdminService clinicalCenterAdminService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private EmailService emailService;

    @PostMapping(value="/saveClinicAdmin/{id_clinic}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<ClinicAdminDTO> saveClinicAdmin(@RequestBody UserDTO userDTO, @PathVariable Long id_clinic) {

        User existUser = this.userService.findByEmail(userDTO.getEmail());
        if (existUser != null) {
            throw new ResourceConflictException(userDTO.getId(), "This email already exists");
        }
        else
        {
            Clinic c=clinicService.findOne(id_clinic);
            ClinicAdmin clinicAdmin= new ClinicAdmin();

            clinicAdmin.setEmail(userDTO.getEmail());
            clinicAdmin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            clinicAdmin.setName(userDTO.getName());
            clinicAdmin.setSurname(userDTO.getSurname());
            clinicAdmin.setAddress(userDTO.getAddress());
            clinicAdmin.setCity(userDTO.getCity());
            clinicAdmin.setCountry(userDTO.getCountry());
            clinicAdmin.setPhoneNumber(userDTO.getPhoneNumber());
            clinicAdmin.setSerialNumber(userDTO.getSerialNumber());
            clinicAdmin.setEnabled(true);
            clinicAdmin.setClinic(c);

            List<Authority> auth = authService.findByname("ROLE_CLINIC_ADMIN");
            clinicAdmin.setAuthorities(auth);

            clinicAdmin = clinicAdminService.saveClinicAdmin(clinicAdmin);
            return new ResponseEntity<>(new ClinicAdminDTO(clinicAdmin), HttpStatus.CREATED);
        }
    }

    @PostMapping(value="/saveClinicalCenterAdmin", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<ClinicalCenterAdminDTO> saveClinicalCenterAdmin(@RequestBody UserDTO userDTO) {

        User existUser = this.userService.findByEmail(userDTO.getEmail());
        if (existUser != null) {
            throw new ResourceConflictException(userDTO.getId(), "This email already exists");
        }
        else
        {
            ClinicalCenterAdmin clinicalCenterAdmin = new ClinicalCenterAdmin();

            clinicalCenterAdmin.setEmail(userDTO.getEmail());
            clinicalCenterAdmin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            clinicalCenterAdmin.setName(userDTO.getName());
            clinicalCenterAdmin.setSurname(userDTO.getSurname());
            clinicalCenterAdmin.setAddress(userDTO.getAddress());
            clinicalCenterAdmin.setCity(userDTO.getCity());
            clinicalCenterAdmin.setCountry(userDTO.getCountry());
            clinicalCenterAdmin.setPhoneNumber(userDTO.getPhoneNumber());
            clinicalCenterAdmin.setSerialNumber(userDTO.getSerialNumber());
            clinicalCenterAdmin.setEnabled(true);

            List<Authority> auth = authService.findByname("ROLE_CLINICAL_CENTER_ADMIN");
            clinicalCenterAdmin.setAuthorities(auth);

            clinicalCenterAdmin = clinicalCenterAdminService.saveClinicalCenterAdmin(clinicalCenterAdmin);
            return new ResponseEntity<>(new ClinicalCenterAdminDTO(clinicalCenterAdmin), HttpStatus.CREATED);
        }
    }

    @PostMapping(value="/saveClinic", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> saveClinic(@RequestBody ClinicDTO clinic) {
        Clinic existClinic = this.clinicService.findByName(clinic.getName());
        if (existClinic == null)
        {
            Clinic newClinic = new Clinic();
            newClinic.setName(clinic.getName());
            newClinic.setAddress(clinic.getAddress());
            newClinic.setDescription(clinic.getDescription());

            newClinic = clinicService.saveClinic(newClinic);
            return new ResponseEntity<>(new ClinicDTO(newClinic), HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

    }

    @RequestMapping(value="/getNewRequests", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> getNewRequests()
    {
        List<User> users = clinicalCenterAdminService.findNewRequests("UNRESOLVED");

        List<UserDTO> usersDTO = new ArrayList<>();
        for(User u : users)
        {
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PutMapping(value="/acceptRequest/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> acceptRequest(@PathVariable Long id, UriComponentsBuilder ucBuilder)
    {

        Patient p = patientService.findOne(id);
        if(p!=null)
        {
            p.setActivatedAccount("ACCEPTED");
            p.setMedicalRecord(new MedicalRecord());
            p = patientService.save(p);
            try {

                String appUrl =  ucBuilder.toUriString();
                System.out.println(appUrl);
                OnRegistrationSuccessEvent event = new OnRegistrationSuccessEvent(p,appUrl);
                emailService.sendNotificaitionAsync(p,event);
            }catch( Exception e ){
                logger.info("Sending activation link to user email error: " + e.getMessage());
            }
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PutMapping(value="/rejectRequest/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id,@RequestBody String message ,UriComponentsBuilder ucBuilder)
    {

        Patient p = patientService.findOne(id);
        if(p!=null){
            p.setActivatedAccount("REJECTED");
            p = patientService.save(p);
            try {

                String appUrl =  ucBuilder.toUriString();
                System.out.println(appUrl);
                OnRegistrationSuccessEvent event = new OnRegistrationSuccessEvent(p,appUrl);
                emailService.sendMessageAsync(message,p,event);
            }catch( Exception e ){
                logger.info("Sending activation link to user email error: " + e.getMessage());
            }
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

   /* @DeleteMapping(value = "deleteClinicAdmin/{id}")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<Void> deleteClinicAdmin(@PathVariable Long id) {

        ClinicAdmin clinicAdmin = clinicAdminService.findOne(id);

        if (clinicAdmin != null) {
            clinicAdminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteClinicalCenterAdmin/{id}")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<Void> deleteClinicalCenterAdmin(@PathVariable Long id) {

        ClinicalCenterAdmin clinicalCenterAdmin = clinicalCenterAdminService.findOne(id);

        if (clinicalCenterAdmin != null) {
            clinicalCenterAdminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
