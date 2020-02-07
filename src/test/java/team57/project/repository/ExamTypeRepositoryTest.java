package team57.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.ExamType;
import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExamTypeRepositoryTest {

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Test
    public void findExamByName(){

        ExamType type = this.examTypeRepository.findByName("CT snimanje glave");
        assertThat(type).isNotNull();
        assertThat(type.getName()).isEqualTo("CT snimanje glave");
    }


}
