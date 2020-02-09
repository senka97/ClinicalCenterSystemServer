package team57.project.service;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.dto.DoctorFA;
import team57.project.dto.MERoomRequest;
import team57.project.dto.MedicalExamRequest;
import team57.project.dto.RoomME;
import team57.project.model.*;
import team57.project.repository.MedicalExamRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.repository.TermRoomRepository;
import team57.project.service.impl.DoctorServiceImpl;
import team57.project.service.impl.MedicalExamServiceImpl;
import team57.project.service.impl.RoomServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;


import javax.mail.MessagingException;
import javax.transaction.Transactional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class MedicalExamServiceTest {

    @Autowired
    private MedicalExamServiceImpl medicalExamService;
    @MockBean
    private MedicalExamRepository medicalExamRepositoryMocked;
    @MockBean
    private TermRoomRepository termRoomRepositoryMocked;
    @MockBean
    private TermDoctorRepository termDoctorRepositoryMocked;
    @MockBean
    private RoomServiceImpl roomServiceMocked;
    @MockBean
    private DoctorServiceImpl doctorServiceMocked;
    @MockBean
    private EmailService emailServiceMocked;


    @Test
    public void testFindExamRequests(){
        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setId(2L);
        Room r = new Room("Room 2","2","Medical exam",false);
        r.setId(1L);
        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                e,p,r,d,clinic);
        m1.setId(1L);
        List<MedicalExam> ms = new ArrayList<MedicalExam>();
        ms.add(m1);
        Mockito.when(medicalExamRepositoryMocked.findExamRequests(clinic.getId())).thenReturn(ms);
        List<MedicalExamRequest> medicalExamsRequests = medicalExamService.findExamRequests(clinic);
        Assert.assertEquals(1,medicalExamsRequests.size());
        Assert.assertEquals(medicalExamsRequests.get(0).getId(),m1.getId());

        verify(medicalExamRepositoryMocked,times(1)).findExamRequests(clinic.getId());

    }

    @Test
    public void testFindOne(){
        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                null,null,null,null,clinic);
        m1.setId(1L);

        Mockito.when(medicalExamRepositoryMocked.findById(m1.getId())).thenReturn(java.util.Optional.of(m1));
        MedicalExam m2 = medicalExamService.findOne(m1.getId());
        assertThat(m2.getId()).isEqualTo(m1.getId());
        assertThat(m2.getStatusME()).isEqualTo(m1.getStatusME());

        verify(medicalExamRepositoryMocked,times(1)).findById(m1.getId());

    }

    @Test
    public void testGetAvailableRooms(){

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                null,null,null,null,clinic);
        m1.setId(1L);
        List<Room> rooms = new ArrayList<Room>();
        Room r1 = new Room("Room 1","1","Medical exam",false);
        r1.setId(1L);
        Room r2 = new Room("Room 2","2","Medical exam",false);
        r2.setId(2L);
        Room r3 = new Room("Room 3","3","Medical exam",false);
        r3.setId(3L);
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        clinic.getRooms().add(r1);
        clinic.getRooms().add(r2);
        clinic.getRooms().add(r3);
        Mockito.when(medicalExamRepositoryMocked.findAvailableRooms(clinic.getId(),m1.getDate(),m1.getStartTime(),m1.getEndTime())).thenReturn(rooms);
        List<RoomME> roomME = medicalExamService.getAvailableRooms(m1);
        Assert.assertEquals(roomME.size(),rooms.size());

        verify(medicalExamRepositoryMocked,times(1)).findAvailableRooms(clinic.getId(),m1.getDate(),m1.getStartTime(),m1.getEndTime());
    }

    @Test
    @Transactional
    public void testReserveRoom() throws MessagingException {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(1L);
        Doctor d1 = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d1.setId(2L);
        TermDoctor td1 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),true,d1);

        Doctor d2 = new Doctor("Ivan","Ivanovic","ivan@gmail.com","ivan123","Gogoljeva 23","Novi Sad","Srbija","23423234234","1231231231111");
        d2.setId(2L);
        TermDoctor td2 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),true,d2);

        Room r = new Room("Room 2","2","Medical exam",false);
        r.setId(1L);
        TermRoom tr1 = new TermRoom(LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),true,r);

        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                e,p,null,d1,clinic);
        m1.setId(1L);

        MedicalExamRequest mer1 = new MedicalExamRequest(m1); //zahtev koji je poslat na front sa zeljenim podacima
        MedicalExamRequest mer2 = new MedicalExamRequest(m1); // zahtev sa fronta sa mogucim podacima, nesto se promenilo
        mer2.setDate(LocalDate.of(2020,12,2));
        mer2.setDoctor(new DoctorFA(d2)); //promenjen je doktor
        //promenili su se datum i vreme
        RoomME rm = new RoomME(1L,"Room 2","2",LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),"Medical exam");
        MERoomRequest mrr = new MERoomRequest(mer1,mer2,rm);

        Mockito.when(termRoomRepositoryMocked.findTermRoom(mrr.getRoomME().getDate(),mrr.getRoomME().getStartTime(),mrr.getRoomME().getId())).thenReturn(tr1);
        //Promenilo se i vreme i datum, drugi slucaj
        Mockito.when(termDoctorRepositoryMocked.findTermDoctor(mer1.getDate(),mer1.getStartTime(),mer1.getDoctor().getId())).thenReturn(td1);
        Mockito.when(termDoctorRepositoryMocked.findTermDoctor(mer2.getDate(),rm.getStartTime(),mer2.getDoctor().getId())).thenReturn(td2);
        Mockito.when(roomServiceMocked.findOne(rm.getId())).thenReturn(r);
        Mockito.when(medicalExamRepositoryMocked.findById(m1.getId())).thenReturn(java.util.Optional.of(m1));
        Mockito.when(doctorServiceMocked.findOne(mer2.getDoctor().getId())).thenReturn(d2);

        //MedicalExam m2 = medicalExamService.reserveRoom(mrr);
        String msg = medicalExamService.reserveRoom(mrr);
        //Long actual = 2L;
        //Assert.assertEquals(m2.getDoctor().getId(),actual);
        //assertThat(m2.getDate()).isEqualTo(LocalDate.of(2020,12,2));
        //assertThat(m2.getStartTime()).isEqualTo(LocalTime.of(6,0));
        //assertThat(m2.getStatusME()).isEqualTo("APPROVED");
        assertThat(msg).isEqualTo(null);
    }

    @Test
    @Transactional
    public void testReserveRoomNegative() throws MessagingException {

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        ExamType e = new ExamType("Pregled 1","Pregled broj 1",2342,34);
        e.setId(1L);
        Patient p = new Patient("Pera","Peric","pera@gmail.com","pera123","Gogoljeva 12","Novi Sad","Srbija","234234234","1231231231231");
        p.setId(1L);
        Doctor d1 = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d1.setId(2L);
        TermDoctor td1 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),true,d1);

        //zauzet mu je termin
        Doctor d2 = new Doctor("Ivan","Ivanovic","ivan@gmail.com","ivan123","Gogoljeva 23","Novi Sad","Srbija","23423234234","1231231231111");
        d2.setId(2L);
        TermDoctor td2 = new TermDoctor(LocalDate.of(2020,11,2),LocalTime.of(7,0),LocalTime.of(8,0),false,d2);

        Room r = new Room("Room 2","2","Medical exam",false);
        r.setId(1L);
        TermRoom tr1 = new TermRoom(LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),true,r);

        MedicalExam m1 = new MedicalExam(LocalDate.of(2020,11,2),
                LocalTime.of(7,0),LocalTime.of(8,0),"REQUESTED",5000,10,
                e,p,null,d1,clinic);
        m1.setId(1L);

        MedicalExamRequest mer1 = new MedicalExamRequest(m1); //zahtev koji je poslat na front sa zeljenim podacima
        MedicalExamRequest mer2 = new MedicalExamRequest(m1); // zahtev sa fronta sa mogucim podacima, nesto se promenilo
        mer2.setDate(LocalDate.of(2020,12,2));
        mer2.setDoctor(new DoctorFA(d2)); //promenjen je doktor
        //promenili su se datum i vreme
        RoomME rm = new RoomME(1L,"Room 2","2",LocalDate.of(2020,12,2),LocalTime.of(6,0),LocalTime.of(7,0),"Medical exam");
        MERoomRequest mrr = new MERoomRequest(mer1,mer2,rm);

        Mockito.when(termRoomRepositoryMocked.findTermRoom(mrr.getRoomME().getDate(),mrr.getRoomME().getStartTime(),mrr.getRoomME().getId())).thenReturn(tr1);
        //Promenilo se i vreme i datum, drugi slucaj
        Mockito.when(termDoctorRepositoryMocked.findTermDoctor(mer1.getDate(),mer1.getStartTime(),mer1.getDoctor().getId())).thenReturn(td1);
        Mockito.when(termDoctorRepositoryMocked.findTermDoctor(mer2.getDate(),rm.getStartTime(),mer2.getDoctor().getId())).thenReturn(td2);
        Mockito.when(roomServiceMocked.findOne(rm.getId())).thenReturn(r);
        Mockito.when(medicalExamRepositoryMocked.findById(m1.getId())).thenReturn(java.util.Optional.of(m1));
        Mockito.when(doctorServiceMocked.findOne(mer2.getDoctor().getId())).thenReturn(d2);

        //MedicalExam m2 = medicalExamService.reserveRoom(mrr);
        String msg = medicalExamService.reserveRoom(mrr);
        //Long actual = 2L;
        //Assert.assertEquals(m2.getDoctor().getId(),actual);
        //assertThat(m2.getDate()).isEqualTo(LocalDate.of(2020,12,2));
        //assertThat(m2.getStartTime()).isEqualTo(LocalTime.of(6,0));
        //assertThat(m2.getStatusME()).isEqualTo("APPROVED");
        assertThat(msg).isEqualTo("Doctor's term is not free.");
    }
}
