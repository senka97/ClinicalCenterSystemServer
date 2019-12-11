package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import team57.project.event.OnRegistrationSuccessEvent;
import team57.project.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Async
    public void sendNotificaitionAsync(User user, OnRegistrationSuccessEvent event) throws MailException, InterruptedException, MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String confirmationUrl
                =  "http://localhost:9000/auth" + "/confirmRegistration?token=" + token;
        String link = "<a href='" + confirmationUrl + "'>" + confirmationUrl + "</a>";
        String htmlMsg = "Hello, \n " + user.getName() + ",\n\n Please register your email by clicking on link below: " +
                " \n " + link;
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(user.getEmail());
        helper.setSubject("Clinical Center System account activation.");
        helper.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendMessageAsync(User user, OnRegistrationSuccessEvent event) throws MailException, InterruptedException, MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "Hello, \n " + user.getName() + ",\n\n We are sorry to inform you that your registration has been rejected due to our strict policy. " ;
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(user.getEmail());
        helper.setSubject("Clinical Center System account activation.");
        helper.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mimeMessage);
    }
}
