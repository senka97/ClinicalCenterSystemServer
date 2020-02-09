package team57.project;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team57.project.controller.*;
import team57.project.controller.integration.ClinicControllerIntegrationTest;
import team57.project.controller.integration.DoctorControllerIntegrationTest;
import team57.project.controller.integration.ExamTypeControllerIntegrationTest;
import team57.project.controller.integration.PatientControllerIntegrationTest;
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
        RoomControllerTest.class,
        MedicalExamControllerTest.class,
        FastAppointmentControllerTest.class,
        ClinicControllerTest.class,
        DoctorControllerTest.class,
        ExamTypeControllerTest.class,
        PatientControllerTest.class,
        FastAppointmentRepositoryTest.class,
        MedicalExamRepositoryTest.class,
        RoomRepositoryTest.class,
        TermRoomRepositoryTest.class,
        ClinicRepositoryTest.class,
        DoctorRepositoryTest.class,
        ExamTypeRepositoryTest.class,
        TermDoctorRepositoryTest.class,
        FastAppointmentControllerIntegrationTest.class,
        MedicalExamControllerIntegrationTest.class,
        RoomControllerIntegrationTest.class,
        ClinicControllerIntegrationTest.class,
        DoctorControllerIntegrationTest.class,
        ExamTypeControllerIntegrationTest.class,
        PatientControllerIntegrationTest.class

})

public class TestSuite {

}
