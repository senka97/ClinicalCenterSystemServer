package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team57.project.model.User;
import team57.project.service.UserService;

import java.security.Principal;

@RestController
@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/currentUser")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_CLINICAL_CENTER_ADMIN') or hasRole('ROLE_NURSE')")
    public User user() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        System.out.println(email);
        return this.userService.findByEmail(email);
    }
}
