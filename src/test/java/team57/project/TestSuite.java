package team57.project;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team57.project.controller.FastAppointmentControllerTest;
import team57.project.controller.MedicalExamControllerTest;
import team57.project.controller.RoomControllerTest;
import team57.project.service.FastAppointmentServiceTest;
import team57.project.service.MedicalExamServiceTest;
import team57.project.service.RoomServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MedicalExamServiceTest.class,
        RoomServiceTest.class,
        FastAppointmentServiceTest.class,
        MedicalExamControllerTest.class,
        RoomControllerTest.class,
        FastAppointmentControllerTest.class
})
public class TestSuite {

}
