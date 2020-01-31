package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import team57.project.model.Doctor;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.service.impl.TermDoctorServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TermDoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private TermDoctorServiceImpl termDoctorService;




}
