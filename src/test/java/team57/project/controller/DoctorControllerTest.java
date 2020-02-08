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
import team57.project.dto.DoctorRating;
import team57.project.model.*;
import team57.project.service.ClinicService;
import team57.project.service.DoctorService;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.service.impl.ExamTypeServiceImpl;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;
import static team57.project.constants.ClinicConstants.*;
import static team57.project.constants.ClinicConstants.CLINIC_1_RNUM;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DoctorControllerTest {
    private static final String URL_PREFIX = "/api/doctors";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private DoctorServiceImpl doctorService;

    @MockBean
    private ExamTypeServiceImpl examTypeService;

    @InjectMocks
    private DoctorController doctorController;

    @MockBean
    private ClinicService clinicService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(doctorController)
                .build();
    }

    @Test
    public void getFreeDoctors() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        DoctorRating dr = new DoctorRating();
        dr.setId(3L);
        List<DoctorRating> doctorRatings = new ArrayList<>();
        doctorRatings.add(dr);

        Mockito.when(this.clinicService.findOne(CLINIC_1_ID)).thenReturn(clinic1);
        Mockito.when(doctorService.findFreeDoctors(clinic1,request)).thenReturn(doctorRatings);

        String json = "{\"idExamType\":1,\"date\":[2020,2,7]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getFreeDoctors/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void getFreeDoctorsWeek() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        DoctorRating dr = new DoctorRating();
        dr.setId(3L);
        List<DoctorRating> doctorRatings = new ArrayList<>();
        doctorRatings.add(dr);

        Mockito.when(this.clinicService.findOne(CLINIC_1_ID)).thenReturn(clinic1);
        Mockito.when(doctorService.findFreeDoctors(clinic1,request)).thenReturn(doctorRatings);

        String json = "{\"idExamType\":1,\"date\":[2020,2,8]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getFreeDoctors/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAvailableTerms() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        AppointmentDTO dr = new AppointmentDTO();
        dr.setId(3L);
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        appointmentDTOS.add(dr);

        ExamType e1 = new ExamType(1L,"PRVI");

        Mockito.when(doctorService.findFreeTerms(3L,request)).thenReturn(appointmentDTOS);
        Mockito.when(examTypeService.findOne(request.getIdExamType())).thenReturn(e1);

        String json = "{\"idExamType\":1,\"date\":[2020,2,7]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getAvailableTerms/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }
    @Test
    public void getAvailableTermsExeption() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        AppointmentDTO dr = new AppointmentDTO();
        dr.setId(3L);
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        appointmentDTOS.add(dr);

        ExamType e1 = new ExamType(1L,"PRVI");

        Mockito.when(doctorService.findFreeTerms(3L,request)).thenReturn(appointmentDTOS);
        Mockito.when(examTypeService.findOne(request.getIdExamType())).thenReturn(e1);

        String json = "{\"idExamType\":1,\"date\":null," +
                "\"time\":null}";

        mockMvc.perform(post(URL_PREFIX+"/getAvailableTerms/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isIAmATeapot());
    }

}
