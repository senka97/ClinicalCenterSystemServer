package team57.project.service;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import team57.project.event.OnRegistrationSuccessEvent;
import team57.project.model.User;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /*
     * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
     */
    @Autowired
    private Environment env;


    @Autowired
    private UserService userService;

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    @Async
    public void sendNotificaitionAsync(User user, OnRegistrationSuccessEvent event) throws MailException, InterruptedException {

        //Simulacija duze aktivnosti da bi se uocila razlika



        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);





        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
       // mail.setTo(emailUser);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical Center System account activation.");
        String confirmationUrl
                =  "http://localhost:9000/auth" + "/confirmRegistration?token=" + token;

        String url="<a href='"+confirmationUrl+"'>"+confirmationUrl+"</a>";
        mail.setText("Hello, \n " + user.getName() + ",\n\n Please register your email by copy and past url in browser" +
                "on below: \n " + confirmationUrl);
        javaMailSender.send(mail);

        System.out.println("Email sent!");
    }
}
