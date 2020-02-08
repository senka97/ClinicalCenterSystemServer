package team57.project;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team57.project.controller.*;
import team57.project.service.FastAppointmentServiceTest;
import team57.project.service.MedicalExamServiceTest;
import team57.project.service.RoomServiceTest;
import team57.project.repository.*;
import team57.project.service.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MedicalExamServiceTest.class,
        FastAppointmentServiceTest.class,
        RoomServiceTest.class,
        ClinicServiceTest.class,
        DoctorServiceTest.class,
        ExamTypesServiceTest.class,
        PatientServiceTest.class,

        ClinicRepositoryTest.class,
        DoctorRepositoryTest.class,
        ExamTypeRepositoryTest.class,
        TermDoctorRepositoryTest.class,

        MedicalExamControllerTest.class,
        RoomControllerTest.class,
        FastAppointmentControllerTest.class,
        ClinicControllerTest.class,
        DoctorControllerTest.class,
        ExamTypeControllerTest.class,
        PatientControllerTest.class


})

public class TestSuite {

}
