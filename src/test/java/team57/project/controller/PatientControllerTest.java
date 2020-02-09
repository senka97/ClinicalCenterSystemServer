package team57.project.controller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team57.project.ClinicalCenterSystemServerApplication;
import team57.project.dto.AppointmentDTO;
import team57.project.dto.AvailableDoctorRequest;
import team57.project.dto.ClinicDTO;
import team57.project.model.*;
import team57.project.service.ClinicService;
import team57.project.service.impl.ExamTypeServiceImpl;
import team57.project.service.impl.PatientServiceImpl;

import javax.transaction.Transactional;
import java.nio.charset.Charset;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;
import static team57.project.constants.ClinicConstants.*;
import static team57.project.constants.ClinicConstants.CLINIC_1_RNUM;
import static org.mockito.Mockito.*;
import static team57.project.constants.PatientConstants.*;
import static team57.project.constants.PatientConstants.PATIENT_SERIAL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PatientControllerTest {

    private static final String URL_PREFIX = "/api/patients";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private PatientServiceImpl patientService;

    @InjectMocks
    private PatientController patientController;



    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(patientController)
                .build();
    }

    @Test
    public void makeAppointment() throws Exception {


        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setType("1");

        System.out.println("Appointment DTO:" + appointmentDTO);

        Mockito.when(this.patientService.sendAppointment(appointmentDTO,3L)).thenReturn(false);


        String json = "{\"id\":1,\"date\":[2020,2,11]," +
                "\"time\":[11,0],\"type\":1,\"doctorId\":3}";
        mockMvc.perform(put(URL_PREFIX+"/makeAppointment/5")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isGone());
    }
    /*@Test
    public void makeAppointmentTrue() throws Exception {


        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setType("Ultrazvuk kicmenog stuba");

        System.out.println("Appointment DTO:" + appointmentDTO);

        Mockito.when(this.patientService.sendAppointment(appointmentDTO,5L)).thenReturn(true);

        String json = "{\"id\":1,\"date\":[2020,2,6]," +
                "\"time\":[7,0],\"type\": \"Ultrazvuk kicmenog stuba\",\"doctorId\":3}";
        mockMvc.perform(put(URL_PREFIX+"/makeAppointment/5")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }*/
}
