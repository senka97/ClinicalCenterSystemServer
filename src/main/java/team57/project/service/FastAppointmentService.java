package team57.project.service;

import team57.project.dto.AllFastAppointments;
import team57.project.dto.FARequest;
import team57.project.model.Clinic;
import team57.project.model.FastAppointment;

import java.time.LocalDateTime;
import java.util.Collection;

public interface FastAppointmentService {

    //Collection<FastAppointment> findFAWithExamType(Long id, LocalDateTime now);
    void addNewFA(Clinic clinic, FARequest faRequest);
    AllFastAppointments getAllFA(Clinic clinic);
}
