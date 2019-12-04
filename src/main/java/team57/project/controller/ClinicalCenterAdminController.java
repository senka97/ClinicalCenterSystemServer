package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.ClinicAdminDTO;
import team57.project.dto.ClinicalCenterAdminDTO;
import team57.project.dto.UserDTO;
import team57.project.model.Authority;
import team57.project.model.Clinic;
import team57.project.model.ClinicAdmin;
import team57.project.model.ClinicalCenterAdmin;
import team57.project.service.AuthorityService;
import team57.project.service.ClinicAdminService;
import team57.project.service.ClinicService;
import team57.project.service.ClinicalCenterAdminService;

import java.util.List;

@RestController
@RequestMapping(value = "api/clinicalCenterAdmin")
@CrossOrigin("http://localhost:4200")
public class ClinicalCenterAdminController {

    @Autowired
    private ClinicalCenterAdminService clinicalCenterAdminService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @PostMapping(value="/changeInformation", produces="application/json", consumes="application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<ClinicalCenterAdminDTO> changeInformation(@RequestBody ClinicalCenterAdminDTO adminDTO)
    {
        System.out.println(adminDTO.getEmail()+ " " + adminDTO.getPassword());
        ClinicalCenterAdmin admin= clinicalCenterAdminService.findByEmail(adminDTO.getEmail());
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        admin.setName(adminDTO.getName());
        admin.setSurname(adminDTO.getSurname());
        //admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setAddress(adminDTO.getAddress());
        admin.setCity(adminDTO.getCity());
        admin.setCountry(adminDTO.getCountry());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());

        admin = clinicalCenterAdminService.saveClinicalCenterAdmin(admin);
        return new ResponseEntity<>(new ClinicalCenterAdminDTO(admin), HttpStatus.OK);

    }

    @PostMapping(value="/saveClinicAdmin/{id_clinic}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<ClinicAdminDTO> saveClinicAdmin(@RequestBody UserDTO userDTO, @PathVariable Long id_clinic) {

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

    @PostMapping(value="/saveClinicalCenterAdmin", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('CLINICAL_CENTER_ADMIN')")
    public ResponseEntity<ClinicalCenterAdminDTO> saveClinicalCenterAdmin(@RequestBody UserDTO userDTO) {

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

    @DeleteMapping(value = "deleteClinicAdmin/{id}")
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
    }
}
