package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.AllFastAppointments;
import team57.project.dto.FARequest;
import team57.project.dto.FastAppointmentDTO;
import team57.project.dto.FastAppointmentWKDTO;
import team57.project.model.*;
import team57.project.repository.*;
import team57.project.service.ClinicService;
import team57.project.service.EmailService;
import team57.project.service.FastAppointmentService;


import javax.mail.MessagingException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FastAppointmentServiceImpl implements FastAppointmentService {

    @Autowired
    private FastAppointmentRepository fastAppointmentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private TermDoctorRepository termDoctorRepository;
    @Autowired
    private ExamTypeServiceImpl examTypeService;
    @Autowired
    private TermRoomRepository termRoomRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ClinicService clinicService;


    /*public Collection<FastAppointment> findFAWithExamType(Long id, LocalDateTime now){

        return fastAppointmentRepository.findFAWithExamType(id, LocalDateTime.now());
    }*/


    @Override
    public FastAppointment findOne(Long id) {
        return fastAppointmentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addNewFA(Clinic clinic, FARequest faRequest) {

        TermDoctor termDoctor = termDoctorRepository.findTermDoctor(faRequest.getDate(),faRequest.getTime(),faRequest.getIdDoctor());
        TermRoom termRoom = termRoomRepository.findTermRoom(faRequest.getDate(),faRequest.getTime(),faRequest.getIdRoom());
        Room room = roomRepository.findRoom(faRequest.getIdRoom()); //da li treba ovo da zakljucam ili samo termin?
        Doctor doctor = doctorRepository.findDoctor(faRequest.getIdDoctor()); //isto pitanje
        ExamType examType = examTypeService.findOne(faRequest.getIdExamType());

        FastAppointment fa = new FastAppointment(faRequest.getDate(),faRequest.getTime(),1,
                examType,room,doctor,null,examType.getPrice(), examType.getDiscount(),false,clinic);
        fastAppointmentRepository.save(fa);
        termDoctor.setFree(false);
        termDoctorRepository.save(termDoctor);
        termRoom.setFree(false);
        termRoomRepository.save(termRoom);
    }

    @Override
    public AllFastAppointments getAllFA(Clinic clinic) {

        List<FastAppointmentDTO> finishedDTO = new ArrayList<FastAppointmentDTO>();
        List<FastAppointmentDTO> scheduledDTO = new ArrayList<FastAppointmentDTO>();
        List<FastAppointmentDTO> freeDTO = new ArrayList<FastAppointmentDTO>();
        List<FastAppointment> finished = new ArrayList<FastAppointment>();
        List<FastAppointment> scheduled = new ArrayList<FastAppointment>();
        List<FastAppointment> free = new ArrayList<FastAppointment>();
        finished = fastAppointmentRepository.findFinished(clinic.getId(), LocalDate.now(), LocalTime.now().minusHours(1));
        scheduled = fastAppointmentRepository.findScheduled(clinic.getId(), LocalDate.now(),LocalTime.now().minusHours(1));
        free = fastAppointmentRepository.findFree(clinic.getId(),LocalDate.now(),LocalTime.now().minusHours(1));

        for(FastAppointment fa: finished){
            finishedDTO.add(new FastAppointmentDTO(fa));
        }
        for(FastAppointment fa: scheduled){
            scheduledDTO.add(new FastAppointmentDTO(fa));
        }
        for(FastAppointment fa: free){
            freeDTO.add(new FastAppointmentDTO(fa));
        }

        finishedDTO.sort(Comparator.comparing(FastAppointmentDTO::getDate).reversed());
        scheduledDTO.sort(Comparator.comparing(FastAppointmentDTO::getDate).reversed());
        freeDTO.sort(Comparator.comparing(FastAppointmentDTO::getDate).reversed());

        AllFastAppointments allFAs = new AllFastAppointments(finishedDTO,scheduledDTO,freeDTO);
        return allFAs;

    }

    @Override
    public List<FastAppointmentDTO> getFreeFA(Clinic clinic) {

        List<FastAppointmentDTO> freeFADTO = new ArrayList<FastAppointmentDTO>();
        List<FastAppointment> freeFA = fastAppointmentRepository.findFree(clinic.getId(),LocalDate.now(),LocalTime.now());

        for(FastAppointment fa: freeFA){
            freeFADTO.add(new FastAppointmentDTO(fa));
        }

        return freeFADTO;
    }

    @Override
    public List<FastAppointmentWKDTO> getReservedFA(Doctor doctor) {

        List<FastAppointmentWKDTO> freeFADTO = new ArrayList<FastAppointmentWKDTO>();
        List<FastAppointment> reservedFA = fastAppointmentRepository.findReserved(doctor.getId());

        for(FastAppointment fa: reservedFA){
            freeFADTO.add(new FastAppointmentWKDTO(fa));
        }

        return freeFADTO;
    }

    @Override
    @Transactional
    public String reserveFA(FastAppointment fa, Patient patient) throws OptimisticLockException {

        fa.setPatient(patient);
        fa.setReserved(true);
        fastAppointmentRepository.save(fa);
        Clinic clinic = fa.getClinic();
        if(!clinic.getPatients().contains(patient)) {
            clinic.getPatients().add(patient);
        }
        clinicService.saveClinic(clinic);
        try {
            emailService.sendFAReservation(patient, fa);
        }catch(Exception e){
            return "Something went wrong with sending an email.";
       }

        return null;
    }


}
