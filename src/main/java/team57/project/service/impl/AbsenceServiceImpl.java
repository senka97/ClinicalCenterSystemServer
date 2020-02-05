package team57.project.service.impl;

import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.AbsenceRequest;
import team57.project.model.*;
import team57.project.repository.AbsenceRepository;
import team57.project.repository.ClinicRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.service.AbsenceService;
import team57.project.service.ClinicService;
import team57.project.service.EmailService;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TermDoctorRepository termDoctorRepository;

    @Override
    public Absence findOne(Long id) {
        return absenceRepository.findById(id).orElse(null);
    }

    @Override
    public boolean sendRequestDoctor(Doctor doctor, AbsenceRequest absenceRequest) {


        List<TermDoctor> scheduledTerms = this.termDoctorRepository.checkScheduledTerms(doctor.getId(),absenceRequest.getStartDate(),absenceRequest.getEndDate());
        if(scheduledTerms.size() != 0){
            return false;
        }

        Absence absence = new Absence(absenceRequest.getTypeOfAbsence(),"REQUESTED",absenceRequest.getStartDate(),absenceRequest.getEndDate());
        absence.setDoctor(doctor);
        absence.setNurse(null);
        Clinic clinic = doctor.getClinic();
        clinic.getAbsences().add(absence);
        absenceRepository.save(absence);
        clinicRepository.save(clinic);
        return true;

    }

    @Override
    public String approveAbsence(Absence absence) {

        if(absence.getDoctor() != null){
            List<TermDoctor> scheduledTerms = this.termDoctorRepository.checkScheduledTerms(absence.getDoctor().getId(),absence.getStartDate(),absence.getEndDate());
            if(scheduledTerms.size() != 0){
                return "This doctor can't be absent in that period because he/she has scheduled medical exams or surgeries.";
            }
        }

        try{
            emailService.sendMessageApproved(absence);
        } catch (Exception e) {
            return "Something went wrong with sending email notification. Please try again.";
        }

        absence.setStatusOfAbsence("APPROVED");
        absenceRepository.save(absence);
        return null;

    }

    @Override
    public boolean rejectAbsence(Absence absence, String message) {

        try{
            emailService.sendMessageReject(absence, message);
        } catch (Exception e) {
            return false;
        }

        absence.setStatusOfAbsence("REJECTED");
        absenceRepository.save(absence);
        return true;
    }

    @Override
    public boolean sendRequestNurse(Nurse nurse, AbsenceRequest absenceRequest)
    {
        //mozda nesto proveriti

        Absence absence = new Absence(absenceRequest.getTypeOfAbsence(),"REQUESTED",absenceRequest.getStartDate(),absenceRequest.getEndDate());
        absence.setNurse(nurse);
        absence.setDoctor(null);
        Clinic clinic = nurse.getClinic();
        clinic.getAbsences().add(absence);
        clinicRepository.save(clinic);
        return true;
    }

}
