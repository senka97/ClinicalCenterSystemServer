package team57.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.MedicalExam;
import team57.project.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalExamRepositoryTest {

    @Autowired
    private MedicalExamRepository medicalExamRepository;

    @Test
    public void testFindById(){

        MedicalExam mr = medicalExamRepository.findById(1L).orElse(null);
        assertThat(mr).isNotNull();
    }

    @Test
    public void testFindExamRequest(){

        List<MedicalExam> mrs = medicalExamRepository.findExamRequests(1L);
        assertThat(mrs.size()).isEqualTo(3);
    }

    @Test
    public void testFindAvailableRooms1(){

        List<Room> rooms = medicalExamRepository.findAvailableRooms(1L, LocalDate.of(2020,2,7), LocalTime.of(8,0),LocalTime.of(9,0));
        assertThat(rooms.size()).isEqualTo(2);
    }

    @Test
    public void testFindAvailableRooms2(){

        List<Room> rooms = medicalExamRepository.findAvailableRooms(1L, LocalDate.of(2020,2,7), LocalTime.of(7,0),LocalTime.of(8,0));
        assertThat(rooms.size()).isEqualTo(1);
    }

    @Test
    public void testFindAvailableRooms3(){

        List<Room> rooms = medicalExamRepository.findAvailableRooms(1L, LocalDate.of(2020,2,7), LocalTime.of(21,0),LocalTime.of(22,0));
        assertThat(rooms.size()).isEqualTo(0);
    }
}
