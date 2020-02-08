package team57.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.Doctor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test //Positive
    @Transactional
    public void findFreeDoctors(){

        LocalDate date = LocalDate.of(2020, 2,6);
     //   LocalDate date = LocalDate.of(2020, 2,7);
        List<Doctor> doctors = this.doctorRepository.getFreeDoctors(1L,1L,date);
        assertThat(doctors).isNotNull();

        assertThat(doctors.size()).isEqualTo(1);
        for(Doctor d : doctors){
            System.out.println("Clinic " + d.getClinic().getId() + " " + d.getExamTypes().toString());
            assertThat(d.getClinic().getId()).isEqualTo(1L);

        }
    }


    @Test //Positive WEEK
    @Transactional
    public void findFreeDoctorsWeek(){

        LocalDate date = LocalDate.of(2020, 2,9);
        List<Doctor> doctors = this.doctorRepository.getFreeDoctors(1L,1L,date);

        assertThat(doctors).isNullOrEmpty();

    }

    @Test
    public void testFindDoctor(){

        Doctor d = doctorRepository.findDoctor(3L);
        assertThat(d).isNotNull();
        assertThat(d.getName()).isEqualTo("Petar");
    }

    @Test
    public void testFindDoctorNegative(){

        Doctor d = doctorRepository.findDoctor(120L);
        assertThat(d).isNull();
    }
}
