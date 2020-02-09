package team57.project.e2e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    public void testReservingExamRoomSuccess() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("zika@gmail.com");
        loginPage.getPassword().sendKeys("zika");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        clinicAdminHomePage.ensureIsClickableBtnExamRoom();
        clinicAdminHomePage.getBtnExamRoom().click();
        Thread.sleep(1000);
        clinicAdminHomePage.ensureIsVisibleTableExamRequests();
        List<WebElement> rowsBefore = clinicAdminHomePage.getTableExamRequests().findElements(By.name("exam-request-row"));

        clinicAdminHomePage.ensureIsClickableBtnExamRequest();
        clinicAdminHomePage.getBtnExamRequest().click();
        Thread.sleep(1000);
        clinicAdminHomePage.ensureIsVisibleTableExamRoomSuccess();
        clinicAdminHomePage.ensureIsClickableBtnReserve();
        clinicAdminHomePage.getBtnReserve().click();
        Thread.sleep(5000);

        clinicAdminHomePage.ensureIsVisibleTableExamRequests();
        List<WebElement> rows = clinicAdminHomePage.getTableExamRequests().findElements(By.name("exam-request-row"));

        Assertions.assertEquals(rowsBefore.size()-1, rows.size());

    }

    @Test
    public void testReservingExamRoomReject() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("zika@gmail.com");
        loginPage.getPassword().sendKeys("zika");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        clinicAdminHomePage.ensureIsClickableBtnExamRoom();
        clinicAdminHomePage.getBtnExamRoom().click();
        Thread.sleep(1000);
        clinicAdminHomePage.ensureIsVisibleTableExamRequests();
        List<WebElement> rowsBefore = clinicAdminHomePage.getTableExamRequests().findElements(By.name("exam-request-row"));

        //clinicAdminHomePage.ensureIsClickableBtnExamRequest();
        driver.findElement(By.id("7")).click();

        Thread.sleep(2000);
        clinicAdminHomePage.ensureIsClickableBtnReject();
        clinicAdminHomePage.getBtnReject().click();
        Thread.sleep(5000);

        clinicAdminHomePage.ensureIsClickableBtnOk();
        clinicAdminHomePage.getBtnOk().click();

        clinicAdminHomePage.ensureIsVisibleTableExamRequests();
        List<WebElement> rows = clinicAdminHomePage.getTableExamRequests().findElements(By.name("exam-request-row"));

        Assertions.assertEquals(rowsBefore.size()-1, rows.size());

    }
}
