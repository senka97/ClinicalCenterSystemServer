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

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class ClinicControllerTest {
    private static final String URL_PREFIX = "/api/clinics";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private ClinicService clinicService;

    @InjectMocks
    private ClinicController clinicController;



    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(clinicController)
                .build();
    }

    @Test
    public void getFreeClinics() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        Mockito.when(clinicService.findFreeClinics(request)).thenReturn(clinics);

        String json = "{\"idExamType\":1,\"date\":[2020,2,7]," +
                "\"time\":[8,0]}";

        mockMvc.perform(put(URL_PREFIX+"/getFreeClinics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void getFreeClinicsWeek() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        Mockito.when(clinicService.findFreeClinics(request)).thenReturn(null);

        String json = "{\"idExamType\":1,\"date\":[2020,2,9]," +
                "\"time\":[8,0]}";

        mockMvc.perform(put(URL_PREFIX+"/getFreeClinics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getFreeClinicsDateNull() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        Mockito.when(clinicService.findFreeClinics(request)).thenReturn(null);

        String json = "{\"idExamType\":1,\"date\":null," +
                "\"time\":[8,0]}";

        mockMvc.perform(put(URL_PREFIX+"/getFreeClinics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getFreeClinicsTimeNull() throws Exception {
        AvailableDoctorRequest request = new AvailableDoctorRequest();


        Clinic clinic1 = new Clinic(CLINIC_1_ID, CLINIC_1_NAME, CLINIC_1_ADRESS, CLINIC_1_DES, CLINIC_1_RATING, CLINIC_1_RNUM);
        ClinicDTO dto1 = new ClinicDTO(clinic1);
        List<ClinicDTO> clinics = new ArrayList<>();
        clinics.add(dto1);


        Mockito.when(clinicService.findFreeClinics(request)).thenReturn(null);

        String json = "{\"idExamType\":1,\"date\":[2020,2,8]," +
                "\"time\":null}";

        mockMvc.perform(put(URL_PREFIX + "/getFreeClinics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
