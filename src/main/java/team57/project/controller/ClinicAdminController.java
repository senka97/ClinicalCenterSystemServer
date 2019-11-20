package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team57.project.service.ClinicAdminService;

@RestController
@RequestMapping(value = "api/clinicAdmin")
@CrossOrigin("http://localhost:4200")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;

}
