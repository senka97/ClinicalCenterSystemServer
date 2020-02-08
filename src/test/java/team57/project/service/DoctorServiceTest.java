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
import team57.project.repository.TermDoctorRepository;
import team57.project.service.impl.DoctorServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static team57.project.constants.ClinicConstants.*;
import static team57.project.constants.ClinicConstants.CLINIC_3_RNUM;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DoctorServiceTest {

    @Autowired
    private DoctorServiceImpl doctorService;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private TermDoctorRepository termDoctorRepository;

    @Test //Positive
    @Transactional
    public void findFreeDoctorsTest(){

        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);
        Clinic clinic3 = new Clinic(CLINIC_3_ID,CLINIC_3_NAME,CLINIC_3_ADRESS,CLINIC_3_DES,CLINIC_3_RATING,CLINIC_3_RNUM);

        //Exam type
        LocalDate date = LocalDate.of(2020, 2,11);
        ExamType examType = new ExamType();
        examType.setId(2L);

        Set<ExamType> examTypes = new HashSet<>();
        examTypes.add(examType);

        //Doctor with that contains choosen type and terms
        Doctor doctor1 = new Doctor();
        doctor1.setName("JAAAA");
        doctor1.setId(1L);
        doctor1.setExamTypes(examTypes);

        TermDoctor termDoctor = new TermDoctor();
        termDoctor.setFree(true);
        termDoctor.setDateTerm(date);
        termDoctor.setDoctor(doctor1);
        termDoctor.setId(1L);
        //no absences
        doctor1.setAbsences(new HashSet<>());

        Set<TermDoctor> terms = new HashSet<>();
        terms.add(termDoctor);
        doctor1.setTerms(terms);

        Set<Doctor> doctors = new HashSet<>();
        doctors.add(doctor1);

        clinic1.setExamTypes(examTypes);
        clinic1.setDoctors(doctors);

        AvailableDoctorRequest request = new AvailableDoctorRequest();
        request.setIdExamType(2L);



        List<Doctor> doctorsMock = new ArrayList<>(clinic1.getDoctors());

        request.setDate(date);
        Mockito.when(this.doctorRepository.getFreeDoctors(clinic1.getId(),request.getIdExamType(),request.getDate())).thenReturn(doctorsMock);

        List<DoctorRating> doct = this.doctorService.findFreeDoctors(clinic1,request);


        for(DoctorRating d : doct){
            System.out.println(doct.get(0).getName());
        }
        assertThat(doct).isNotNull();
        assertThat(doct.size()).isEqualTo(1);


    }

    @Test//Positive
    public void findFreeTermsTest(){

        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);

        LocalDate date = LocalDate.of(2020, 2,11);
        LocalTime t1 = LocalTime.of(4,0);
        LocalTime t2 = LocalTime.of(5,0);
        LocalTime t3 = LocalTime.of(6,0);
        LocalTime t4 = LocalTime.of(7,0);

        ExamType examType = new ExamType();
        examType.setId(2L);

        Set<ExamType> examTypes = new HashSet<>();
        examTypes.add(examType);

        //Doctor with that contains choosen type and terms
        Doctor doctor1 = new Doctor();
        doctor1.setName("DoctorMock");
        doctor1.setId(1L);
        doctor1.setExamTypes(examTypes);

        TermDoctor termDoctor =  new TermDoctor( date, t1, t2,true, doctor1);
        TermDoctor termDoctor2 =  new TermDoctor( date, t2, t3,true, doctor1);
        TermDoctor termDoctor3 =  new TermDoctor( date, t3, t4,true, doctor1);

        doctor1.setAbsences(new HashSet<>());

        Set<TermDoctor> terms = new HashSet<>();
        terms.add(termDoctor);
        terms.add(termDoctor2);
        terms.add(termDoctor3);
        doctor1.setTerms(terms);

        Set<Doctor> doctors = new HashSet<>();
        doctors.add(doctor1);

        clinic1.setExamTypes(examTypes);
        clinic1.setDoctors(doctors);

        AvailableDoctorRequest request = new AvailableDoctorRequest();
        request.setIdExamType(2L);
        List<TermDoctor> termMocks = new ArrayList<>(doctor1.getTerms());

        request.setDate(date);
        Mockito.when(this.termDoctorRepository.getFreeTerms(clinic1.getId(),request.getIdExamType(),request.getDate())).thenReturn(termMocks);

        List<AppointmentDTO> appointmentDTOS = this.doctorService.findFreeTerms(doctor1.getId(),request);

        for(AppointmentDTO d : appointmentDTOS){
            System.out.println(d.getTime());
        }
        assertThat(appointmentDTOS.size()).isEqualTo(3);

    }

}
