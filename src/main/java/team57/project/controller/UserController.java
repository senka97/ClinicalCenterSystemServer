package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.dto.UserRequest;
import team57.project.exception.ResourceConflictException;
import team57.project.model.User;
import team57.project.service.UserService;
import team57.project.service.impl.CustomUserDetailsService;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customService;

    @RequestMapping("/currentUser")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_CLINICAL_CENTER_ADMIN') or hasRole('ROLE_NURSE')")
    public User user() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        System.out.println(email);
        return this.userService.findByEmail(email);
    }

    @RequestMapping(value = "/editUser", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_CLINICAL_CENTER_ADMIN') or hasRole('ROLE_NURSE')")
    public ResponseEntity<?> editUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        User existUser = this.userService.findByEmail(email);
        if (existUser == null) {
            throw new ResourceConflictException(userRequest.getId(), "Missing user");
        }
        this.userService.updateUser(userRequest, existUser);
        return new ResponseEntity<User>(existUser, HttpStatus.OK);
    }


}
