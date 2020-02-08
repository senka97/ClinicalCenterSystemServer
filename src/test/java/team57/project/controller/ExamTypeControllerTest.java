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
import team57.project.model.*;
import team57.project.service.ClinicService;
import team57.project.service.impl.ExamTypeServiceImpl;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = ClinicalCenterSystemServerApplication.class)
@TestPropertySource("classpath:test.properties")
public class ExamTypeControllerTest {

    private static final String URL_PREFIX = "/api/examTypes";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private ExamTypeServiceImpl examTypeService;

    @InjectMocks
    private ExamTypeController examTypeController;

    @MockBean
    private ClinicService clinicService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(examTypeController)
                .build();
    }

    @Test //Positive return all exam types
    public void getExamTypeText() throws Exception {
        ExamType e1 = new ExamType(1L,"PRVI");
        ExamType e2 = new ExamType(2L,"Drugi");
        List<ExamType> examTypes = new ArrayList<>();
        examTypes.add(e1);
        examTypes.add(e2);

        Mockito.when(this.examTypeService.findAll()).thenReturn(examTypes);


        mockMvc.perform(get(URL_PREFIX+"/getAllExamTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(this.examTypeService, times(1)).findAll();
    }




}
