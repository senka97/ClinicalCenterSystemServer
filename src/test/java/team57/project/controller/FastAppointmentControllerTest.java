package team57.project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team57.project.dto.FastAppointmentDTO;
import team57.project.dto.UserTokenState;
import team57.project.model.*;
import team57.project.security.auth.JwtAuthenticationRequest;
import team57.project.service.ClinicService;
import team57.project.service.impl.FastAppointmentServiceImpl;
import team57.project.service.impl.MedicalExamServiceImpl;
import team57.project.service.impl.PatientServiceImpl;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class FastAppointmentControllerTest {

    private static final String URL_PREFIX = "/api/fastAppointments";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private FastAppointmentServiceImpl fastAppointmentServiceMocked;

    @InjectMocks
    private FastAppointmentController fastAppointmentController;

    @MockBean
    private ClinicService clinicServiceMocked;

    @MockBean
    private PatientServiceImpl patientServiceMocked;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(fastAppointmentController)
                .build();
    }


    @Test
    public void testGetFreeFastAppointments() throws Exception {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Doctor d1 = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d1.setId(1L);
        Room r1 = new Room("Room 1","1","Medical exam",false);
        r1.setId(1L);
        FastAppointment fa1 = new FastAppointment(LocalDate.of(2020,2,25), LocalTime.of(12,0),1,e,r1,d1,null,1000,15,false,clinic);
        fa1.setId(1L);
        FastAppointment fa2 = new FastAppointment(LocalDate.of(2020,2,26), LocalTime.of(12,0),1,e,r1,d1,null,2000,15,false,clinic);
        fa2.setId(2L);
        FastAppointment fa3 = new FastAppointment(LocalDate.of(2020,2,27), LocalTime.of(12,0),1,e,r1,d1,null,3000,15,false,clinic);
        fa3.setId(3L);
        FastAppointment fa4 = new FastAppointment(LocalDate.of(2020,2,28), LocalTime.of(12,0),1,e,r1,d1,null,4000,15,false,clinic);
        fa4.setId(4L);

        FastAppointmentDTO f1 = new FastAppointmentDTO(fa1);
        FastAppointmentDTO f2 = new FastAppointmentDTO(fa2);
        FastAppointmentDTO f3 = new FastAppointmentDTO(fa3);
        FastAppointmentDTO f4 = new FastAppointmentDTO(fa4);
        List<FastAppointmentDTO> fas = new ArrayList<FastAppointmentDTO>();
        fas.add(f1);
        fas.add(f2);
        fas.add(f3);
        fas.add(f4);
        LocalTime nowTime = LocalTime.now();
        LocalDate nowDate = LocalDate.now();

        Mockito.when(clinicServiceMocked.findOne(clinic.getId())).thenReturn(clinic);
        Mockito.when(fastAppointmentServiceMocked.getFreeFA(clinic)).thenReturn(fas);
        mockMvc.perform(get(URL_PREFIX+"/getFreeFA/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    public void testReserveFA() throws Exception {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Doctor d1 = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d1.setId(1L);
        Room r1 = new Room("Room 1","1","Medical exam",false);
        r1.setId(1L);
        FastAppointment fa1 = new FastAppointment(LocalDate.of(2020,2,25), LocalTime.of(12,0),1,e,r1,d1,null,1000,15,false,clinic);
        fa1.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(2L);

        Mockito.when(fastAppointmentServiceMocked.findOne(1L)).thenReturn(fa1);
        Mockito.when(patientServiceMocked.findOne(p.getId())).thenReturn(p);
        Mockito.when(fastAppointmentServiceMocked.reserveFA(fa1,p)).thenReturn(null);
        mockMvc.perform(put(URL_PREFIX+"/reserveFA/1/2"))
                .andExpect(status().isOk());


    }
}
