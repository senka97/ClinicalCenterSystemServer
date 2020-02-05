package team57.project.service;

import team57.project.dto.AbsenceRequest;
import team57.project.model.Absence;
import team57.project.model.Doctor;
import team57.project.model.Nurse;

import java.util.List;

public interface AbsenceService {

    Absence findOne(Long id);
    boolean sendRequestDoctor(Doctor doctor, AbsenceRequest absenceRequest);
    String approveAbsence(Absence absence);
    boolean rejectAbsence(Absence absence, String message);
    List<Absence> findAllUserAbsences(Long id);
    boolean sendRequestNurse(Nurse nurse, AbsenceRequest absenceRequest);
}
