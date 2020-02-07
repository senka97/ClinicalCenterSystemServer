package team57.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static team57.project.constants.ClinicConstants.*;
import static team57.project.constants.ClinicConstants.CLINIC_1_RNUM;
import static team57.project.constants.PatientConstants.*;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.dto.AppointmentDTO;
import team57.project.model.*;
import team57.project.repository.*;
import team57.project.service.impl.PatientServiceImpl;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private TermDoctorRepository termDoctorRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private ExamTypeRepository examTypeRepository;

    @MockBean
    private MedicalExamRepository medicalExamRepository;

    @MockBean
    private ClinicRepository clinicRepository;

    @MockBean
    private ClinicAdminService clinicAdmin;



    @Test
    public void testFindAll() {
        Patient p = new Patient(PATIENT_ID,PATIENT_NAME,PATIENT_SURNAME,PATIENT_EMAIL,PATIENT_PASSWORD,PATIENT_ADDRESS,PATIENT_CITY,
                PATIENT_COUNTRY,PATIENT_PHONE,PATIENT_SERIAL);
        List<Patient> mock = new ArrayList<>();
        mock.add(p);

        Mockito.when(this.patientRepository.findAll()).thenReturn(mock);
        List<Patient> patients = patientService.findAll();

        assertThat(patients).hasSize(1);
    }
    @Test
    public void testFindOne(){
        Patient p = new Patient(PATIENT_ID,PATIENT_NAME,PATIENT_SURNAME,PATIENT_EMAIL,PATIENT_PASSWORD,PATIENT_ADDRESS,PATIENT_CITY,
                PATIENT_COUNTRY,PATIENT_PHONE,PATIENT_SERIAL);

        Mockito.when(this.patientRepository.findById(p.getId())).thenReturn(java.util.Optional.of(p));


        Patient pat = this.patientService.findOne(PATIENT_ID);

        assertThat(pat.getAddress()).isEqualTo(PATIENT_ADDRESS);
        assertThat(pat).isNotNull();
    }

    @Test //Positive !!DATE -> if(today==2020.2.25) then date=2020.2.28.(friday) max date= today+7
    @Transactional
    public void sendAppointmentTest(){
        Patient p = new Patient(PATIENT_ID,PATIENT_NAME,PATIENT_SURNAME,PATIENT_EMAIL,PATIENT_PASSWORD,PATIENT_ADDRESS,PATIENT_CITY,
                PATIENT_COUNTRY,PATIENT_PHONE,PATIENT_SERIAL);

        LocalDate date = LocalDate.of(2020, 2,11);
        LocalTime t1 = LocalTime.of(4,0);
        LocalTime t2 = LocalTime.of(5,0);
        Doctor doctor1 = new Doctor();
        doctor1.setId(3L);

        //provjeriti sta kada je terin zauzet
        TermDoctor termDoctor = new TermDoctor(date,t1,t2,true,doctor1);
        termDoctor.setId(2L);

        AppointmentDTO appointmentDTO = new AppointmentDTO(termDoctor);
        appointmentDTO.setType("TIP1");

        Mockito.when(this.patientRepository.findById(p.getId())).thenReturn(java.util.Optional.of(p));
        Mockito.when(this.doctorRepository.findById(3L)).thenReturn(java.util.Optional.of(doctor1));

        Mockito.when(this.termDoctorRepository.findById(2L)).thenReturn(java.util.Optional.of(termDoctor));
        ExamType examType = new ExamType(1L,"TIP1");
        examType.setDiscount(20);
        examType.setPrice(200);

        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);

        doctor1.setClinic(clinic1);
        Mockito.when(this.examTypeRepository.findByName("TIP1")).thenReturn(examType);


        MedicalExam medicalExam = new MedicalExam(termDoctor);
        Mockito.when(this.patientService.save(p)).thenReturn(p);
        Mockito.when(this.termDoctorRepository.save(termDoctor)).thenReturn(termDoctor);
        Mockito.when(this.medicalExamRepository.save(medicalExam)).thenReturn(medicalExam);

        Set<Patient> patients = new HashSet<>();
        patients.add(p);
        clinic1.setPatients(patients);

        Mockito.when(this.clinicRepository.getOne(1L)).thenReturn(clinic1);



        ClinicAdmin clinicAdmin = new ClinicAdmin(1L,"email@gmail.com", "Admin");
        Set<ClinicAdmin> admins = new HashSet<>();
        admins.add(clinicAdmin);

        Mockito.when(this.clinicAdmin.findClinicAdmins(clinic1.getId())).thenReturn(admins);


        Boolean sent = this.patientService.sendAppointment(appointmentDTO,p.getId());
        assertThat(sent).isEqualTo(true);
    }



    @Test // Positive retrun FALSE - because term is already taken
    //Positive !!DATE -> if(today==2020.2.25) then date=2020.2.28.(friday) max date= today+7
    @Transactional
    public void sendAppointmentTestTaken(){
        Patient p = new Patient(PATIENT_ID,PATIENT_NAME,PATIENT_SURNAME,PATIENT_EMAIL,PATIENT_PASSWORD,PATIENT_ADDRESS,PATIENT_CITY,
                PATIENT_COUNTRY,PATIENT_PHONE,PATIENT_SERIAL);

        LocalDate date = LocalDate.of(2020, 2,11);
        LocalTime t1 = LocalTime.of(4,0);
        LocalTime t2 = LocalTime.of(5,0);
        Doctor doctor1 = new Doctor();
        doctor1.setId(3L);

        //provjeriti sta kada je terin zauzet
        TermDoctor termDoctor = new TermDoctor(date,t1,t2,false,doctor1);
        termDoctor.setId(2L);

        AppointmentDTO appointmentDTO = new AppointmentDTO(termDoctor);
        appointmentDTO.setType("TIP1");


        System.out.println("Slobodan: ?? : " +termDoctor.isFree());
        Mockito.when(this.patientRepository.findById(p.getId())).thenReturn(java.util.Optional.of(p));
        Mockito.when(this.doctorRepository.findById(3L)).thenReturn(java.util.Optional.of(doctor1));

        Mockito.when(this.termDoctorRepository.findById(2L)).thenReturn(java.util.Optional.of(termDoctor));
        ExamType examType = new ExamType(1L,"TIP1");
        examType.setDiscount(20);
        examType.setPrice(200);

        Clinic clinic1 = new Clinic(CLINIC_1_ID,CLINIC_1_NAME,CLINIC_1_ADRESS,CLINIC_1_DES,CLINIC_1_RATING,CLINIC_1_RNUM);

        doctor1.setClinic(clinic1);
        Mockito.when(this.examTypeRepository.findByName("TIP1")).thenReturn(examType);


        MedicalExam medicalExam = new MedicalExam(termDoctor);
        Mockito.when(this.patientService.save(p)).thenReturn(p);
        Mockito.when(this.termDoctorRepository.save(termDoctor)).thenReturn(termDoctor);
        Mockito.when(this.medicalExamRepository.save(medicalExam)).thenReturn(medicalExam);

        Set<Patient> patients = new HashSet<>();
        patients.add(p);
        clinic1.setPatients(patients);

        Mockito.when(this.clinicRepository.getOne(1L)).thenReturn(clinic1);


        ClinicAdmin clinicAdmin = new ClinicAdmin(1L,"email@gmail.com", "Admin");
        Set<ClinicAdmin> admins = new HashSet<>();
        admins.add(clinicAdmin);

        Mockito.when(this.clinicAdmin.findClinicAdmins(clinic1.getId())).thenReturn(admins);


        Boolean sent = this.patientService.sendAppointment(appointmentDTO,p.getId());
        assertThat(sent).isEqualTo(false);
    }


    private List<Authority> getPatientAuthority() {
        Authority patientAuthority = new Authority();
        patientAuthority.setName(PATIENT_TYPE);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(patientAuthority);

        return authorities;
    }
}
