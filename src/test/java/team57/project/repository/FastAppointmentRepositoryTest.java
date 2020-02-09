package team57.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.FastAppointment;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FastAppointmentRepositoryTest {


    @Autowired
    private FastAppointmentRepository fastAppointmentRepository;


    @Test
    public void testFindFree(){

        //List<FastAppointment> fa = fastAppointmentRepository.findFree(1L, LocalDate.of(2020,2,5),LocalTime.of(9,0));
        List<FastAppointment> fa = fastAppointmentRepository.findFree(1L);
        assertThat(fa.size()).isEqualTo(3);
    }

}
