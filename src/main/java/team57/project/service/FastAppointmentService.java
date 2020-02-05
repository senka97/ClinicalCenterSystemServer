package team57.project.service;

import team57.project.dto.AllFastAppointments;
import team57.project.dto.FARequest;
import team57.project.dto.FastAppointmentDTO;
import team57.project.dto.FastAppointmentWKDTO;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.FastAppointment;
import team57.project.model.Patient;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface FastAppointmentService {

    //Collection<FastAppointment> findFAWithExamType(Long id, LocalDateTime now);
    FastAppointment findOne(Long id);
    void addNewFA(Clinic clinic, FARequest faRequest);
    AllFastAppointments getAllFA(Clinic clinic);
    List<FastAppointmentDTO> getFreeFA(Clinic clinic);
    String reserveFA(FastAppointment fa,Patient patient) throws Exception;
    List<FastAppointmentWKDTO> getReservedFA(Doctor doctor);
}
