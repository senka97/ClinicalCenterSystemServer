package team57.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.Clinic;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicRepositoryTest {

    @Autowired
    private ClinicRepository clinicRepository;

    @Test //Positive
    public void findFreeClinicsTest(){
     //   LocalDate date = LocalDate.of(2020, 2,7);
        LocalDate date = LocalDate.of(2020, 2,6);
        List<Clinic> clinics=this.clinicRepository.getFreeClinics(3L,date);

        assertThat(clinics).isNotNull();
        assertThat(clinics.size()).isEqualTo(1);
    }

    @Test //Negative - 0 clinics
    public void findFreeClinicsTestWeek(){
        //week
        LocalDate date = LocalDate.of(2020, 2,9);
        List<Clinic> clinics=this.clinicRepository.getFreeClinics(1L,date);

        assertThat(clinics).isNotNull();
        assertThat(clinics.size()).isEqualTo(0);
    }
    @Test //Negative - 0 clinics
    public void findFreeClinicsTestMissingType(){
        //week
        LocalDate date = LocalDate.of(2020, 2,8);
        List<Clinic> clinics=this.clinicRepository.getFreeClinics(1000L,date);

        assertThat(clinics).isNotNull();
        assertThat(clinics.size()).isEqualTo(0);
    }
    @Test
    public void findFreeClinicsNull(){
        List<Clinic> clinics=this.clinicRepository.getFreeClinics(null,null);
        assertThat(clinics).isNullOrEmpty();
    }
}
