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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.dto.UserRequest;
import team57.project.dto.UserTokenState;
import team57.project.exception.ResourceConflictException;
import team57.project.model.User;
import team57.project.security.TokenUtils;
import team57.project.security.auth.JwtAuthenticationRequest;
import team57.project.service.UserService;
import team57.project.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
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
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
