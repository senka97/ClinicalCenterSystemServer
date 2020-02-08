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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team57.project.dto.FreeTermsRequest;
import team57.project.dto.RoomME;
import team57.project.model.Clinic;
import team57.project.service.ClinicService;
import team57.project.service.impl.RoomServiceImpl;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RoomControllerTest {

    private static final String URL_PREFIX = "/api/rooms";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private RoomServiceImpl roomServiceMocked;

    @MockBean
    private ClinicService clinicServiceMocked;

    @InjectMocks
    private RoomController roomController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(roomController)
                .build();
    }

    @Test
    public void testFindRoomsFreeTerms() throws Exception {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);

        List<RoomME> foundRooms = new ArrayList<RoomME>();
        FreeTermsRequest ftr = new FreeTermsRequest("Room 1","1",1L, LocalDate.of(2020,11,2));

        //RoomME rm1 = new RoomME(1L,"Room 1","1",LocalDate.of(2020,11,2), LocalTime.of(7,0),LocalTime.of(8,0),"Medical exam");
       // RoomME rm2 = new RoomME(2L,"Room 2","2",LocalDate.of(2020,11,2), LocalTime.of(8,0),LocalTime.of(9,0),"Medical exam");

        //foundRooms.add(rm1);
        //foundRooms.add(rm2);

        String json = "{\"roomName\":\"Roo\",\"roomNumber\":\"\",\"idDoctor\":1,\"date\":[2020,11,2]}";
        Mockito.when(clinicServiceMocked.findOne(clinic.getId())).thenReturn(clinic);
        Mockito.when(roomServiceMocked.findRoomsFreeTerms(clinic,ftr)).thenReturn(foundRooms);
        mockMvc.perform(post(URL_PREFIX+"/findRoomsFreeTerms/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
