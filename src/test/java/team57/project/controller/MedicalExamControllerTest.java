package team57.project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team57.project.TestUtil;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.security.auth.JwtAuthenticationRequest;
import team57.project.service.ClinicService;
import team57.project.service.impl.MedicalExamServiceImpl;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class MedicalExamControllerTest {

    private static final String URL_PREFIX = "/api/medicalExams";

    private String accessToken;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void login() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("zika.zikic789@gmail.com", "zika"),
                        UserTokenState.class);
        accessToken = "Bearer " + Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        System.out.println(accessToken);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @MockBean
    private MedicalExamServiceImpl medicalExamServiceMocked;

    @MockBean
    private ClinicService clinicServiceMocked;



    @Test
    public void testGetExamRequest() throws Exception {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setId(2L);
        Room r = new Room("Room 2","2","Medical exam",false);
        r.setId(1L);
        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                e,p,r,d,clinic);
        m1.setId(1L);

        Mockito.when(medicalExamServiceMocked.findOne(1L)).thenReturn(m1);
        mockMvc.perform(get(URL_PREFIX+"/getExamRequest/1")
                .header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date[0]").value(2020))
                .andExpect(jsonPath("$.date[1]").value(11))
                .andExpect(jsonPath("$.date[2]").value(2))
                .andExpect(jsonPath("$.startTime[0]").value(7))
                .andExpect(jsonPath("$.startTime[1]").value(0))
                .andExpect(jsonPath("$.fullNamePatient").value("Pera Peric"))
                .andExpect(jsonPath("$.doctor.id").value(2L))
                .andExpect(jsonPath("$.doctor.fullName").value("Petar Petrovic"))
                .andExpect(jsonPath("$.examTypeName").value("Pregled 1"))
                .andExpect(jsonPath("$.idExamType").value(1L));

        verify(medicalExamServiceMocked,times(1)).findOne(1l);

    }

    @Test
    public void testGetExamRequests() throws Exception {
        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setId(2L);
        Room r = new Room("Room 2","2","Medical exam",false);
        r.setId(1L);
        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                e,p,r,d,clinic);
        m1.setId(1L);

        MedicalExamRequest mer = new MedicalExamRequest(m1);
        List<MedicalExamRequest> mers = new ArrayList<MedicalExamRequest>();
        mers.add(mer);

        Long idM = 1L;
        Long idD = 2L;
        Long idE = 1L;
        Mockito.when(clinicServiceMocked.findOne(clinic.getId())).thenReturn(clinic);
        Mockito.when(medicalExamServiceMocked.findExamRequests(clinic)).thenReturn(mers);
        mockMvc.perform(get(URL_PREFIX+"/getExamRequests/1")
                .header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(idM.intValue())))
                .andExpect(jsonPath("$.[*].date[0]").value(2020))
                .andExpect(jsonPath("$.[*].date[1]").value(11))
                .andExpect(jsonPath("$.[*].date[2]").value(2))
                .andExpect(jsonPath("$.[*].startTime[0]").value(7))
                .andExpect(jsonPath("$.[*].startTime[1]").value(0))
                .andExpect(jsonPath("$.[*].fullNamePatient").value(hasItem("Pera Peric")))
                .andExpect(jsonPath("$.[*].doctor.id").value(hasItem(idD.intValue())))
                .andExpect(jsonPath("$.[*].doctor.fullName").value(hasItem("Petar Petrovic")))
                .andExpect(jsonPath("$.[*].examTypeName").value(hasItem("Pregled 1")))
                .andExpect(jsonPath("$.[*].idExamType").value(hasItem(idE.intValue())));

        verify(clinicServiceMocked,times(1)).findOne(clinic.getId());
        verify(medicalExamServiceMocked,times(1)).findExamRequests(clinic);

    }



        @Test
        public void testGetAvailableRooms() throws Exception {
            Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
            clinic.setId(1L);
            MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                    LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                    null,null,null,null,clinic);
            m1.setId(1L);
            Room r1 = new Room("Room 1","1","Medical exam",false);
            r1.setId(1L);
            Room r2 = new Room("Room 2","2","Medical exam",false);
            r2.setId(2L);
            Room r3 = new Room("Room 3","3","Medical exam",false);
            r3.setId(3L);
            clinic.getRooms().add(r1);
            clinic.getRooms().add(r2);
            clinic.getRooms().add(r3);

            RoomME rm1 = new RoomME(r1.getId(),r1.getName(),r1.getNumber(),m1.getDate(),m1.getStartTime(),m1.getEndTime(),r1.getRoomType());
            RoomME rm2 = new RoomME(r2.getId(),r2.getName(),r2.getNumber(),m1.getDate(),m1.getStartTime(),m1.getEndTime(),r2.getRoomType());
            RoomME rm3 = new RoomME(r3.getId(),r3.getName(),r3.getNumber(),m1.getDate(),m1.getStartTime(),m1.getEndTime(),r3.getRoomType());
            List<RoomME> rooms = new ArrayList<RoomME>();
            rooms.add(rm1);
            rooms.add(rm2);
            rooms.add(rm3);
            Mockito.when(medicalExamServiceMocked.findOne(m1.getId())).thenReturn(m1);
            Mockito.when(medicalExamServiceMocked.getAvailableRooms(m1)).thenReturn(rooms);

            mockMvc.perform(get(URL_PREFIX+"/getAvailableRoomsExam/1")
                    .header("Authorization", accessToken))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$", hasSize(3)));

            verify(medicalExamServiceMocked,times(1)).findOne(m1.getId());
            verify(medicalExamServiceMocked,times(1)).getAvailableRooms(m1);

        }


        @Test
        public void testReserveRoom() throws Exception {

            Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
            clinic.setId(1L);
            ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
            e.setId(1L);
            Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
            p.setId(1L);
            Doctor d1 = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
            d1.setId(2L);
            TermDoctor td1 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),true,d1);

            Doctor d2 = new Doctor("Ivan","Ivanovic","ivan@gmail.com","ivan123","Gogoljeva 23","Novi Sad","Srbija","23423234234","1231231231111");
            d2.setId(2L);
            TermDoctor td2 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),true,d2);

            Room r = new Room("Room 2","2","Medical exam",false);
            r.setId(1L);
            TermRoom tr1 = new TermRoom(LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),true,r);

            MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                    LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                    e,p,null,d1,clinic);
            m1.setId(1L);

            MedicalExamRequest mer1 = new MedicalExamRequest(m1); //zahtev koji je poslat na front sa zeljenim podacima
            MedicalExamRequest mer2 = new MedicalExamRequest(m1); // zahtev sa fronta sa mogucim podacima, nesto se promenilo
            mer2.setDate(LocalDate.of(2020,12,2));
            mer2.setDoctor(new DoctorFA(d2)); //promenjen je doktor
            //promenili su se datum i vreme
            RoomME rm = new RoomME(1L,"Room 2","2",LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),"Medical exam");
            MERoomRequest mrr = new MERoomRequest(mer1,mer2,rm);

            String json = "{\"examStart\":{\"id\":1,\"date\":[2020,11,2],\"startTime\":[7,0],\"fullNamePatient\":\"Pera Peric\",\"doctor\":{\"id\":2,\"fullName\":\"Petar Petrovic\"},\"examTypeName\":\"Pregled 1\",\"idExamType\":1},\"examEnd\":{\"id\":1,\"date\":[2020,12,2],\"startTime\":[7,0],\"fullNamePatient\":\"Pera Peric\",\"doctor\":{\"id\":2,\"fullName\":\"Ivan Ivanovic\"},\"examTypeName\":\"Pregled 1\",\"idExamType\":1},\"roomME\":{\"id\":1,\"name\":\"Room 2\",\"number\":\"2\",\"date\":[2020,12,2],\"startTime\":[6,0],\"endTime\":[7,0],\"roomType\":\"Medical exam\"}}";
            Mockito.when(medicalExamServiceMocked.reserveRoom(mrr)).thenReturn(null);
            mockMvc.perform(put(URL_PREFIX+"/reserveRoom")
                    .header("Authorization", accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json))
                    .andExpect(status().isOk());


        }
    }


