package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.FastAppointment;
import team57.project.repository.FastAppointmentRepository;
import team57.project.service.FastAppointmentService;


import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class FastAppointmentServiceImpl implements FastAppointmentService {

    @Autowired
    private FastAppointmentRepository fastAppointmentRepository;


    /*public Collection<FastAppointment> findFAWithExamType(Long id, LocalDateTime now){

        return fastAppointmentRepository.findFAWithExamType(id, LocalDateTime.now());
    }*/
}
