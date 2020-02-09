package team57.project.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team57.project.TestUtil;
import team57.project.dto.AvailableDoctorRequest;
import team57.project.dto.UserTokenState;
import team57.project.model.Clinic;
import team57.project.security.auth.JwtAuthenticationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DoctorControllerIntegrationTest {
    private static final String URL_PREFIX = "/api/doctors";

    private String accessToken;

    @Autowired
    private TestRestTemplate restTemplate;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Before
    public void login() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/auth/login",
                        new JwtAuthenticationRequest("isa2019pacijent@outlook.com", "pera"),
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
    @Test
    public void getFreeDoctors() throws Exception {

        String json = "{\"idExamType\":1,\"date\":[2020,2,6]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getFreeDoctors/1")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getFreeDoctorsNone() throws Exception {

        String json = "{\"idExamType\":1,\"date\":[2010,2,1]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getFreeDoctors/1")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    public void getFreeDoctorsWeek() throws Exception {

        String json = "{\"idExamType\":1,\"date\":[2020,2,9]," +
                "\"time\":[8,0]}";
        mockMvc.perform(post(URL_PREFIX+"/getFreeDoctors/1")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest());
        //"You can't reserve a doctor at the weekend."
    }
    @Test
    public void getAvailableTerms() throws Exception {

        String json = "{\"idExamType\":2,\"date\":[2020,2,6]," +
                "\"time\":[8,0]}";

        mockMvc.perform(post(URL_PREFIX+"/getAvailableTerms/3")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));


    }
    @Test
    public void getAvailableTermsError() throws Exception {

        String json = "{\"idExamType\":2,\"date\":null," +
                "\"time\":null}";

        mockMvc.perform(post(URL_PREFIX+"/getAvailableTerms/3")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isIAmATeapot());


    }

}
