package team57.project;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team57.project.e2e.ReservingExamTypeRoomTest;
import team57.project.e2e.SearchingClinicsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SearchingClinicsTest.class,
        ReservingExamTypeRoomTest.class
})
public class TestSuiteE2E {
}
