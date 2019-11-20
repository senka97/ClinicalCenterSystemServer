package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.ClinicAdminDTO;
import team57.project.dto.ClinicalCenterAdminDTO;
import team57.project.dto.UserDTO;
import team57.project.model.Clinic;
import team57.project.model.ClinicAdmin;
import team57.project.model.ClinicalCenterAdmin;
import team57.project.service.ClinicAdminService;
import team57.project.service.ClinicService;
import team57.project.service.ClinicalCenterAdminService;

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

    @PostMapping(value="/saveClinicAdmin/{id_clinic}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ClinicAdminDTO> saveClinicAdmin(@RequestBody UserDTO userDTO, @PathVariable Long id_clinic) {

        Clinic c=clinicService.findOne(id_clinic);
        ClinicAdmin clinicAdmin = new ClinicAdmin(userDTO.getName(),userDTO.getSurname(),userDTO.getEmail(),userDTO.getPassword(),userDTO.getAddress(),
                userDTO.getCity(),userDTO.getCountry(),userDTO.getPhoneNumber(),userDTO.getSerialNumber(),c);


        clinicAdmin = clinicAdminService.saveClinicAdmin(clinicAdmin);
        return new ResponseEntity<>(new ClinicAdminDTO(clinicAdmin), HttpStatus.CREATED);

    }

    @PostMapping(value="/saveClinicalCenterAdmin", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ClinicalCenterAdminDTO> saveClinicalCenterAdmin(@RequestBody UserDTO userDTO) {

        ClinicalCenterAdmin clinicalCenterAdmin = new ClinicalCenterAdmin(userDTO.getName(),userDTO.getSurname(),userDTO.getEmail(),userDTO.getPassword(),userDTO.getAddress(),
                userDTO.getCity(),userDTO.getCountry(),userDTO.getPhoneNumber(),userDTO.getSerialNumber());

        clinicalCenterAdmin = clinicalCenterAdminService.saveClinicalCenterAdmin(clinicalCenterAdmin);
        return new ResponseEntity<>(new ClinicalCenterAdminDTO(clinicalCenterAdmin), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "deleteClinicAdmin/{id}")
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
