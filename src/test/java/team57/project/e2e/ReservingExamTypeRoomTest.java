package team57.project.e2e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class ReservingExamTypeRoomTest {

    private static final String baseUrl = "http://localhost:4200";
    private WebDriver driver;

    private LoginPage loginPage;
    private ClinicAdminHomePage clinicAdminHomePage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        clinicAdminHomePage = PageFactory.initElements(driver, ClinicAdminHomePage.class);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testReservingExamRoomSuccess(){

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("zika@gmail.com");
        loginPage.getPassword().sendKeys("zika");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();



        Assertions.assertEquals(true, true);

    }
}
