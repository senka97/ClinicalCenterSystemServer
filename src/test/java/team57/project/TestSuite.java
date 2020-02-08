package team57.project;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team57.project.controller.FastAppointmentControllerTest;
import team57.project.controller.MedicalExamControllerTest;
import team57.project.controller.RoomControllerTest;
import team57.project.service.FastAppointmentServiceTest;
import team57.project.service.MedicalExamServiceTest;
import team57.project.service.RoomServiceTest;
import team57.project.repository.*;
import team57.project.service.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MedicalExamServiceTest.class,
        RoomServiceTest.class,
        FastAppointmentServiceTest.class,
        MedicalExamControllerTest.class,
        RoomControllerTest.class,
        FastAppointmentControllerTest.class,
        ClinicServiceTest.class,
        DoctorServiceTest.class,
        ExamTypesServiceTest.class,
        PatientServiceTest.class,
        ClinicRepositoryTest.class,
        DoctorRepositoryTest.class,
        ExamTypeRepositoryTest.class,
        TermDoctorRepositoryTest.class
})

public class TestSuite {

}
