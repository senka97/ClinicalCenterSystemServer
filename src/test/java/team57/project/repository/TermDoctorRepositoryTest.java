package team57.project.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.model.Doctor;
import team57.project.model.TermDoctor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TermDoctorRepositoryTest {

    @Autowired
    private TermDoctorRepository termDoctorRepository;

    @Test //Positive
    public void findFreeTermsDate(){
        //LocalDate date = LocalDate.of(2020, 2,7);

        LocalDate date = LocalDate.of(2020, 2,6);
        List<TermDoctor> terms = this.termDoctorRepository.getFreeTerms(3L,1L,date);
        assertThat(terms).isNotNull();
        //System create 8 terms each day of next week from today - Mock database 3 free Terms
        assertThat(terms.size()).isEqualTo(3);
        for(TermDoctor term : terms){
            //same doctor
            assertThat(term.getDoctor().getId()).isEqualTo(3L);
            //And term is free
            assertThat(term.isFree()).isEqualTo(true);
        }
    }

    @Test //Positive
    public void findFreeTermsTaken(){
        LocalDate date = LocalDate.of(2020, 2,5);
        //LocalDate date = LocalDate.of(2020, 2,6);
        List<TermDoctor> terms = this.termDoctorRepository.getFreeTerms(3L,1L,date);
        assertThat(terms).isNullOrEmpty();
    }

    @Test //Positive empty
    public void findFreeTermsDateWeek(){
        LocalDate date = LocalDate.of(2020, 2,9);
        List<TermDoctor> terms = this.termDoctorRepository.getFreeTerms(3L,1L,date);
        assertThat(terms).isNotNull();

        assertThat(terms).isNullOrEmpty();

    }
    @Test //Positive empty - wrong id
    public void findFreeTermsDatePatientId(){
        LocalDate date = LocalDate.of(2020, 2,6);
        List<TermDoctor> terms = this.termDoctorRepository.getFreeTerms(5L,1L,date);
        assertThat(terms).isNotNull();
        //System create 8 terms each day of next week from today
        assertThat(terms).isNullOrEmpty();
    }

    @Test
    @Transactional
    public void testFindTermDoctor(){

        TermDoctor td = termDoctorRepository.findTermDoctor(LocalDate.of(2020,2,7), LocalTime.of(7,0),3L);
        assertThat(td).isNotNull();
        assertThat(td.getDoctor().getId()).isEqualTo(3L);
    }

    @Test
    @Transactional
    public void testFindTermDoctor1(){

        TermDoctor td = termDoctorRepository.findTermDoctor(LocalDate.of(2020,2,7), LocalTime.of(23,0),3L);
        assertThat(td).isNull();
    }
}
