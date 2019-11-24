package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.dto.UserRequest;
import team57.project.dto.UserTokenState;
import team57.project.event.OnRegistrationSuccessEvent;
import team57.project.exception.ResourceConflictException;
import team57.project.model.User;
import team57.project.model.VerificationToken;
import team57.project.security.TokenUtils;
import team57.project.security.auth.JwtAuthenticationRequest;
import team57.project.service.EmailService;
import team57.project.service.UserService;
import team57.project.service.impl.CustomUserDetailsService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Controller
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws AuthenticationException, IOException {

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Ubaci email + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);// ako su ispravni, korisnika treba da upisemo u security context holder
        //odnosno da nasa aplikacija bude svesna da je korisnik ulogovan
        // Kreiraj token
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail()); //kreiranje tokena je odredjeno u ovoj tokenUtils klasi, koju smo mi napisali
        int expiresIn = tokenUtils.getExpiredIn(); //koja ima funkcije za generisanje i validiranje jwt tokena


        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn)); //kroz ovaj dto objekat se salje token na klijent
    }

    @RequestMapping(method = POST, value = "/signup")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "This email already exists");
        }


        User user = this.userService.save(userRequest);

        try {

            String appUrl =  ucBuilder.toUriString();
            System.out.println(appUrl);
            OnRegistrationSuccessEvent event = new OnRegistrationSuccessEvent(user,appUrl);
            emailService.sendNotificaitionAsync(user,event);
        }catch( Exception e ){
            logger.info("Sending activation link to user email error: " + e.getMessage());
        }

     //   user.setSurname("registracija");
        //User notification should be moved to admin

        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        System.out.println("registration confirm started");

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "NULL verification tonen";
            model.addAttribute("message", message);
            return "redirect:/badUser.html";
        }

        User user = verificationToken.getUser();

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue ="Token expired";
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html";
        }

        user.setEnabled(true);
        userService.enableRegisteredUser(user);
        return null;
    }
}
