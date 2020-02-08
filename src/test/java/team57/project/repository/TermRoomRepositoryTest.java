package team57.project.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.TermRoom;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TermRoomRepositoryTest {

    @Autowired
    private TermRoomRepository termRoomRepository;


    @Test
    @Transactional
    public void testTermRoom(){

        TermRoom tr = termRoomRepository.findTermRoom(LocalDate.of(2020,2,7),LocalTime.of(7,0),1L);
        assertThat(tr).isNotNull();
        assertThat(tr.getRoom().getId()).isEqualTo(1L);
    }


    @Test
    @Transactional
    public void testTermRoom1(){

        TermRoom tr = termRoomRepository.findTermRoom(LocalDate.of(2020,2,7),LocalTime.of(23,0),1L);
        assertThat(tr).isNull();
    }
}
