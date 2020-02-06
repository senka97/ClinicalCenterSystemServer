package team57.project.service;


import team57.project.dto.*;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.ExamType;

import java.util.List;

public interface DoctorService {

    Doctor findOne(Long id);
    Doctor findByEmail(String email);
    String doctorExists(String email, String serialNumber);
    void addDoctor(DoctorDTO doctorDTO, Clinic clinic);
    boolean removeDoctor(Doctor doctor);
    List<DoctorSearch> searchForDoctors(DoctorSearch doctorSearch, Long clinicId);
    List<DoctorSearch> getAllDoctors(Long idClinic);
    List<DoctorRating> getAllDoctorsRating(Long idClinic);

    Doctor save(Doctor d);

    Doctor rateDoctor(Long doctorId, RateDTO rate);
    void probaZaScheduling();

    List<DoctorFA> findAvailableDoctors(Clinic clinic,AvailableDoctorRequest adr);


    List<DoctorRating> findFreeDoctors(Clinic clinic, AvailableDoctorRequest adr);
    List<AppointmentDTO> findFreeTerms(Long doctorId, AvailableDoctorRequest adr);
<<<<<<< HEAD
    Boolean sendSurgeryAppointment(Long patientId,AppointmentDTO appointmentDTO);
=======

    List<DoctorFA> searchForDoctorsExamTypes(Clinic clinic, ExamType examType);

>>>>>>> master
}
