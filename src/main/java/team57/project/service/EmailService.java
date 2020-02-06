package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import team57.project.dto.MedicalExamRequest;
import team57.project.dto.RoomME;
import team57.project.event.OnRegistrationSuccessEvent;
import team57.project.model.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;
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
    public void sendMessageAsync(String message,User user, OnRegistrationSuccessEvent event) throws MailException, InterruptedException, MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        //String htmlMsg = "Hello, \n " + user.getName() + ",\n\n We are sorry to inform you that your registration has been rejected due to our strict policy. " ;
        helper.setText(message, true); // Use this or above line.
        helper.setTo(user.getEmail());
        helper.setSubject("Clinical Center System account activation.");
        helper.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendMessageApproved(Absence absence) throws MailException, InterruptedException, MessagingException {

        String name;
        String email;
        if(absence.getDoctor() != null){
            name = absence.getDoctor().getName();
            email = absence.getDoctor().getEmail();
        }else{
            name = absence.getNurse().getName();
            email = absence.getNurse().getEmail();
        }
        SimpleMailMessage mail = new SimpleMailMessage();
        String msg = "Hello, " + name + "!\n\nYour request for absence has been approved.\n\nBest regards,\nClinic admin";
        mail.setText(msg);
        mail.setTo(email);
        mail.setSubject("Clinical Center System - absence request");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);
    }

    @Async
    public void sendMessageReject(Absence absence, String message) throws MailException, InterruptedException, MessagingException {

        String name;
        String email;
        if(absence.getDoctor() != null){
            name = absence.getDoctor().getName();
            email = absence.getDoctor().getEmail();
        }else{
            name = absence.getNurse().getName();
            email = absence.getNurse().getEmail();
        }
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText("Hello, " + name + "!\n\n" + message + "\n\nBest regards,\nClinic admin");
        mail.setTo(email);
        mail.setSubject("Clinical Center System - absence request");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);
    }

    @Async
    public void sendFAReservation(Patient patient, FastAppointment fa) throws MailException, InterruptedException, MessagingException{
        String date = fa.getDateFA().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String time = fa.getTimeFA().format(DateTimeFormatter.ofPattern("hh:mm"));
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText("Hello, " + patient.getName() + "!\n\n" + "You have successfully reserved the medical exam - " + fa.getExamType().getName() + " on " + date + " at " + time + "."  + "\n\nBest regards,\nClinic admin");
        mail.setTo(patient.getEmail());
        mail.setSubject("Clinical Center System - Medical exam reservation");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);
    }
    @Async
    public void notificationAppointmentReq(Patient patient, Doctor doctor, MedicalExam medicalExam, Set<ClinicAdmin> admins) throws MailException, InterruptedException, MessagingException{

        String date = medicalExam.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

        String time = medicalExam.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        SimpleMailMessage mail = new SimpleMailMessage();
        for(ClinicAdmin admin : admins){
            mail.setText("Hello, " + admin.getName() + "!\n\n" + "Patient : " + patient.getName() + " " + patient.getSurname() +
                    " with serial number : " + patient.getSerialNumber() + " has send request for appoinment!\n\n" +
                   "Appointment: \n Date: " + date + "\nTime: " + time + "\n Doctor: " + doctor.getName() + " "
                    + doctor.getSurname() + "\n Doctor id: " + doctor.getId());
            mail.setTo(admin.getEmail());
            mail.setSubject("Request for appointment");
            mail.setFrom(env.getProperty("spring.mail.username"));
            javaMailSender.send(mail);

        }

    }

    @Async
    public void sendRejectExam(MedicalExam me) throws MailException, InterruptedException, MessagingException{
        String date = me.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String time = me.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText("Hello, " + me.getPatient().getName() + "!\n\n" + "Your request for the medical exam - " + me.getExamType().getName() + " on " + date + " at " + time + " is rejected because currently is not possible to find free doctor and free room in this and the next week."  + "\n\nBest regards,\nClinic admin");
        mail.setTo(me.getPatient().getEmail());
        mail.setSubject("Clinical Center System - Medical exam rejection");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);

    }

    @Async
    public void  sendNotificationForReservation(MedicalExamRequest merNew,MedicalExamRequest merOld, RoomME roomME,String emailP) throws MessagingException {
        String msg = "";
        boolean changed = false;
        if(!merNew.getDate().equals(merOld.getDate()) || !merOld.getStartTime().equals(roomME.getStartTime())){
            msg += "Date of you reservation for medical exam - " + merNew.getExamTypeName() + " was changed on " + merNew.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " at " + roomME.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"))  +  ". Doctor: " + merNew.getDoctor().getFullName() + ".<br>";
            changed = true;
        }
        String dateOld = merOld.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String timeOld = merOld.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));

        String message = "";
        if(!changed){
            message += "Hello, " + merNew.getFullNamePatient() + "<br>" + "You have successfully reserved medical exam - " + merNew.getExamTypeName() + " on " + dateOld + " at " + timeOld + " in room " + roomME.getName() + ". Doctor: " + merNew.getDoctor().getFullName() + ".<br><br>";
        }else{
            message += "Hello, " + merNew.getFullNamePatient() + "<br>" +  msg;
        }

        String urlAccept = "http://localhost:4200/acceptReservation/" +  merNew.getId();
        String linkAccept = "<a href='" + urlAccept + "'>" + urlAccept + "</a>";
        String urlReject = "http://localhost:4200/rejectReservation/" +  merNew.getId();
        String linkReject = "<a href='" + urlReject + "'>" + urlReject + "</a>";

        String finalMessage = message + "If you want this reservation please click on the link below.<br>" + linkAccept + "<br> If you don't want this reservation please click on the link below.<br>" + linkReject + "<br>";
        finalMessage += "Best regards,<br> Clinic admin.";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(finalMessage, true);
        helper.setTo(emailP);
        helper.setSubject("Clinical Center System account activation.");
        helper.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mimeMessage);

    }

    @Async
    public void sendPatientRoom(MedicalExam me) throws MailException, InterruptedException, MessagingException{

        String date = me.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String time = me.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText("Hello, " + me.getPatient().getName() + "!\n\n" + "You have the medical exam - " + me.getExamType().getName() + " on " + date + " at " + time + " in " + me.getExamRoom().getName()  + ".\n\nBest regards,\nClinic admin");
        mail.setTo(me.getPatient().getEmail());
        mail.setSubject("Clinical Center System - Room found");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);

        }

    @Async
    public void sendDoctorRoom(MedicalExam me) throws MailException, InterruptedException, MessagingException{

        String date = me.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String time = me.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText("Hello, " + me.getDoctor().getName() + "!\n\n" + "You have the medical exam - " + me.getExamType().getName() + " on " + date + " at " + time + " in " + me.getExamRoom().getName()  + ".\n\nBest regards,\nClinic admin");
        mail.setTo(me.getDoctor().getEmail());
        mail.setSubject("Clinical Center System - Room found");
        mail.setFrom(env.getProperty("spring.mail.username"));
        javaMailSender.send(mail);

    }


}
