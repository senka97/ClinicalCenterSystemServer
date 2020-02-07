package team57.project.service;

import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import team57.project.dto.FreeTermsRequest;
import team57.project.dto.RoomME;
import team57.project.model.*;
import team57.project.repository.DoctorRepository;
import team57.project.repository.RoomRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.repository.TermRoomRepository;
import team57.project.service.impl.RoomServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RoomServiceTest {

    @Autowired
    private RoomServiceImpl roomService;
    @MockBean
    private RoomRepository roomRepositoryMocked;
    @MockBean
    private DoctorRepository doctorRepositoryMocked;
    @MockBean
    private TermDoctorRepository termDoctorRepositoryMocked;
    @MockBean
    private TermRoomRepository termRoomRepositoryMocked;


    @Test
    public void testFindRoomsFreeTermsPositive(){

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setWorkingHoursStart(LocalTime.of(6,0));
        d.setWorkingHoursEnd(LocalTime.of(14,0));
        d.setId(1L);
        TermDoctor td1 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(7,0),LocalTime.of(8,0),true,d);
        TermDoctor td2 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,d);
        TermDoctor td3 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,d);
        TermDoctor td4 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(10,0),LocalTime.of(11,0),true,d);
        List<TermDoctor> termsDoctor = new ArrayList<TermDoctor>();
        termsDoctor.add(td1);
        termsDoctor.add(td2);
        termsDoctor.add(td3);
        termsDoctor.add(td4);
        Room r1 = new Room("Room 1","1","Medical exam",false);
        TermRoom tr1 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,r1);
        TermRoom tr2 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,r1);
        List<TermRoom> termsRoom1 = new ArrayList<TermRoom>();
        termsRoom1.add(tr1);
        termsRoom1.add(tr2);
        Room r2 = new Room("Room 2","2","Medical exam",false);
        TermRoom tr3 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(10,0),LocalTime.of(11,0),true,r2);
        TermRoom tr4 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(11,0),LocalTime.of(12,0),true,r2);
        List<TermRoom> termsRoom2 = new ArrayList<TermRoom>();
        termsRoom2.add(tr3);
        termsRoom2.add(tr4);

        clinic.getRooms().add(r1);
        clinic.getRooms().add(r2);
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        FreeTermsRequest ftr = new FreeTermsRequest("Ro","",1L, LocalDate.of(2020,2,15));
        Mockito.when(roomRepositoryMocked.findRooms(clinic.getId())).thenReturn(rooms);
        Mockito.when(doctorRepositoryMocked.findDoctor(d.getId())).thenReturn(d);
        Mockito.when(termDoctorRepositoryMocked.findFreeTermsDate(d.getId(),ftr.getDate())).thenReturn(termsDoctor);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r1.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom1);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r1.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom2);

        //trazi se soba koja pocinje na Ro i dobijaju se prvi termin tog dana za tog doktora za svaku sobu koja odgovara pretrazi
        List<RoomME> foundRooms = roomService.findRoomsFreeTerms(clinic,ftr);
        assertThat(foundRooms.size()).isEqualTo(2);

    }

    @Test
    public void testFindRoomsFreeTerms1(){

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setWorkingHoursStart(LocalTime.of(6,0));
        d.setWorkingHoursEnd(LocalTime.of(14,0));
        d.setId(1L);
        TermDoctor td1 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(7,0),LocalTime.of(8,0),true,d);
        TermDoctor td2 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,d);
        TermDoctor td3 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,d);

        List<TermDoctor> termsDoctor = new ArrayList<TermDoctor>();
        termsDoctor.add(td1);
        termsDoctor.add(td2);
        termsDoctor.add(td3);
        Room r1 = new Room("Room 1","1","Medical exam",false);
        r1.setId(1L);
        TermRoom tr1 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,r1);
        TermRoom tr2 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,r1);
        List<TermRoom> termsRoom1 = new ArrayList<TermRoom>();
        termsRoom1.add(tr1);
        termsRoom1.add(tr2);
        Room r2 = new Room("Room 2","2","Medical exam",false);
        r2.setId(2L);
        TermRoom tr3 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(10,0),LocalTime.of(11,0),true,r2);
        TermRoom tr4 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(11,0),LocalTime.of(12,0),true,r2);
        List<TermRoom> termsRoom2 = new ArrayList<TermRoom>();
        termsRoom2.add(tr3);
        termsRoom2.add(tr4);

        clinic.getRooms().add(r1);
        clinic.getRooms().add(r2);
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        FreeTermsRequest ftr = new FreeTermsRequest("Ro","",1L, LocalDate.of(2020,2,15));
        Mockito.when(roomRepositoryMocked.findRooms(clinic.getId())).thenReturn(rooms);
        Mockito.when(doctorRepositoryMocked.findDoctor(d.getId())).thenReturn(d);
        Mockito.when(termDoctorRepositoryMocked.findFreeTermsDate(d.getId(),ftr.getDate())).thenReturn(termsDoctor);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r1.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom1);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r2.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom2);

        //trazi se soba koja pocinje na Ro i dobijaju se prvi termin tog dana za tog doktora za svaku sobu koja odgovara pretrazi
        List<RoomME> foundRooms = roomService.findRoomsFreeTerms(clinic,ftr);
        assertThat(foundRooms.size()).isEqualTo(1);
        assertThat(foundRooms.get(0).getStartTime()).isEqualTo(LocalTime.of(8,0));

    }

    @Test
    public void testFindRoomsFreeTermsNegativeSearch(){

        Clinic clinic = new Clinic("Klinika 1","Aleske Santica 34","Klinika za oci");
        clinic.setId(1L);
        Doctor d = new Doctor("Petar","Petrovic","petar@gmail.com","petar123","Gogoljeva 23","Novi Sad","Srbija","234234234","1231231231231");
        d.setWorkingHoursStart(LocalTime.of(6,0));
        d.setWorkingHoursEnd(LocalTime.of(14,0));
        d.setId(1L);
        TermDoctor td1 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(7,0),LocalTime.of(8,0),true,d);
        TermDoctor td2 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,d);
        TermDoctor td3 = new TermDoctor(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,d);

        List<TermDoctor> termsDoctor = new ArrayList<TermDoctor>();
        termsDoctor.add(td1);
        termsDoctor.add(td2);
        termsDoctor.add(td3);
        Room r1 = new Room("Room 1","1","Medical exam",false);
        r1.setId(1L);
        TermRoom tr1 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(8,0),LocalTime.of(9,0),true,r1);
        TermRoom tr2 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(9,0),LocalTime.of(10,0),true,r1);
        List<TermRoom> termsRoom1 = new ArrayList<TermRoom>();
        termsRoom1.add(tr1);
        termsRoom1.add(tr2);
        Room r2 = new Room("Room 2","2","Medical exam",false);
        r2.setId(2L);
        TermRoom tr3 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(10,0),LocalTime.of(11,0),true,r2);
        TermRoom tr4 = new TermRoom(LocalDate.of(2020,2,15), LocalTime.of(11,0),LocalTime.of(12,0),true,r2);
        List<TermRoom> termsRoom2 = new ArrayList<TermRoom>();
        termsRoom2.add(tr3);
        termsRoom2.add(tr4);

        clinic.getRooms().add(r1);
        clinic.getRooms().add(r2);
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);
        FreeTermsRequest ftr = new FreeTermsRequest("blablabla","",1L, LocalDate.of(2020,2,15));
        Mockito.when(roomRepositoryMocked.findRooms(clinic.getId())).thenReturn(rooms);
        Mockito.when(doctorRepositoryMocked.findDoctor(d.getId())).thenReturn(d);
        Mockito.when(termDoctorRepositoryMocked.findFreeTermsDate(d.getId(),ftr.getDate())).thenReturn(termsDoctor);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r1.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom1);
        Mockito.when(termRoomRepositoryMocked.findFreeTermsDate(r2.getId(),ftr.getDate(),d.getWorkingHoursStart(),d.getWorkingHoursEnd())).thenReturn(termsRoom2);

        //trazi se soba koja pocinje na blablabla i dobijaju se prvi termin tog dana za tog doktora za svaku sobu koja odgovara pretrazi
        List<RoomME> foundRooms = roomService.findRoomsFreeTerms(clinic,ftr);
        assertThat(foundRooms.size()).isEqualTo(0);

    }
}
