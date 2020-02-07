package team57.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import team57.project.dto.AppointmentDTO;
import team57.project.dto.AvailableDoctorRequest;
import team57.project.dto.ClinicDTO;
import team57.project.dto.DoctorRating;
import team57.project.model.Clinic;
import team57.project.model.Doctor;
import team57.project.model.ExamType;
import team57.project.model.TermDoctor;
import team57.project.repository.DoctorRepository;
import team57.project.repository.ExamTypeRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.service.impl.ExamTypeServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static team57.project.constants.ClinicConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ExamTypesServiceTest {
    @Autowired
    private ExamTypeServiceImpl examTypeService;

    @MockBean
    private ExamTypeRepository examTypeRepository;

    @Test
    public void findAllTest(){
        ExamType e1 = new ExamType(1L,"PRVI");
        ExamType e2 = new ExamType(2L,"Drugi");
        ExamType e3 = new ExamType(3L,"Treci");

        List<ExamType> mock = new ArrayList<>();
        mock.add(e1);
        mock.add(e2);
        mock.add(e3);
        Mockito.when(this.examTypeRepository.findAll()).thenReturn(mock);


        List<ExamType> exams = this.examTypeService.findAll();
        for(ExamType exam : exams){
            System.out.println(exam.getName());
        }

        assertThat(exams).isNotNull();
        assertThat(exams.size()).isEqualTo(3);
    }

    @Test
    public void findOne(){
        ExamType e1 = new ExamType(1L,"PRVI");

        Mockito.when(this.examTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(e1));
        ExamType exam = this.examTypeService.findOne(1L);

        assertThat(exam).isNotNull();
        assertThat(exam.getName()).isEqualTo("PRVI");
        assertThat(exam.getId()).isEqualTo(1L);
    }

    @Test
    public void findByNameTest(){
        ExamType e1 = new ExamType(1L,"TESTPRVI");

        Mockito.when(this.examTypeRepository.findByName("TESTPRVI")).thenReturn(e1);
        ExamType exam = this.examTypeService.findByName("TESTPRVI");

        assertThat(exam).isNotNull();
        assertThat(exam.getName()).isEqualTo("TESTPRVI");
        assertThat(exam.getId()).isEqualTo(1L);

    }

}
