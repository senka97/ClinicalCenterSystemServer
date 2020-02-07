package team57.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.test.context.TestPropertySource;

import static team57.project.constants.PatientConstants.*;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.Authority;
import team57.project.model.Patient;
import team57.project.service.impl.PatientServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PatientServiceTest {
    @Autowired
    private PatientServiceImpl patientService;

    @Test
    public void testFindAll() {
        List<Patient> patients = patientService.findAll();

        assertThat(patients).hasSize(1);
    }
    @Test
    public void testFindOne(){
//        Patient p = this.patientService.findOneByEmail(PATIENT_EMAIL);
        Patient p = this.patientService.findOne(PATIENT_ID);
        assertThat(p.getAddress()).isEqualTo(PATIENT_ADDRESS);
    }

    private List<Authority> getPatientAuthority() {
        Authority patientAuthority = new Authority();
        patientAuthority.setName(PATIENT_TYPE);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(patientAuthority);

        return authorities;
    }
}
