package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.AbsenceRequest;
import team57.project.model.*;
import team57.project.repository.AbsenceRepository;
import team57.project.repository.ClinicRepository;
import team57.project.service.AbsenceService;
import team57.project.service.ClinicService;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public Absence findOne(Long id) {
        return absenceRepository.findById(id).orElse(null);
    }

    @Override
    public boolean sendRequestDoctor(Doctor doctor, AbsenceRequest absenceRequest) {

        for(FastAppointment fa: doctor.getFastAppointments()){
            if(fa.getDateTime().toLocalDate().isAfter(absenceRequest.getStartDate().minusDays(1)) &&
            fa.getDateTime().toLocalDate().isBefore(absenceRequest.getEndDate().plusDays(1))){
                   return false;
            }
        }
        //ovde treba jos proveriti za obicne preglede i operacije

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
