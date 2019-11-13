package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team57.project.service.UserService;

@RestController
//@RequestMapping(value = "api/students") kopirano iz njenog primera, videti sta ovde treba
public class UserController {

    @Autowired
    private UserService userService;
}
