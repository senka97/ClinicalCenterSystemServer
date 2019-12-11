package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team57.project.dto.ClinicDTO;
import team57.project.model.Clinic;
import team57.project.model.Patient;
import team57.project.service.ClinicAdminService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinicAdmins")
@CrossOrigin("http://localhost:4200")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;


    @RequestMapping(value = "/getMyClinic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ClinicDTO getMyClinic() {

          Clinic myClinic = clinicAdminService.findMyClinic();
          ClinicDTO clinicDTO = new ClinicDTO(myClinic.getId(), myClinic.getName(), myClinic.getAddress(), myClinic.getDescription(), myClinic.getRating(), myClinic.getNumberOfReviews());
          return clinicDTO;
    }

}
