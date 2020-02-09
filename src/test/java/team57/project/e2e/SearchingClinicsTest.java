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

public class SearchingClinicsTest {

    private static final String baseUrl = "http://localhost:4200";
    private WebDriver driver;

    private LoginPage loginPage;
    private PatientHomePage patientHomePage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        patientHomePage = PageFactory.initElements(driver, PatientHomePage.class);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginPatientError() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("peraa");

        loginPage.getLoginBtn().click();

        Assertions.assertEquals(baseUrl +"/login" , driver.getCurrentUrl());

    }

   /* @Test
    public void testLoginPatientSuccess() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("pera");

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        Assertions.assertEquals(baseUrl +"/patientHP" , driver.getCurrentUrl());

    }*/

    @Test
    public void testPatientSearchingClinicsError() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("pera");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        patientHomePage.ensureIsClickableBtnListOfClinics();
        patientHomePage.getBtnListOfClinics().click();

        //patientHomePage.ensureIsVisibleShowListOfClinics();

        patientHomePage.ensureIsClickableSelectExamType();
        patientHomePage.getSelectExamType().click();

        patientHomePage.ensureIsClickableItemId();
        patientHomePage.getItemId().click();

        patientHomePage.getDatePickerInput().click();
        patientHomePage.getDatePickerInput().sendKeys("2020-02-23");

        patientHomePage.getBtnSearch().click();
        //patientHomePage.ensureIsNotVisibleDivListClinics();
        patientHomePage.ensureIsVisibleDivNoClinis();

        List<WebElement> rows = patientHomePage.getDivListClinics().findElements(By.className("clinics"));

        Assertions.assertEquals(0, rows.size());

    }

    @Test
    public void testPatientSearchingClinicsSuccess() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("pera");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        patientHomePage.ensureIsClickableBtnListOfClinics();
        patientHomePage.getBtnListOfClinics().click();

        patientHomePage.ensureIsVisibleShowListOfClinics();

        patientHomePage.ensureIsClickableSelectExamType();
        patientHomePage.getSelectExamType().click();

        patientHomePage.ensureIsClickableItemId();
        patientHomePage.getItemId().click();

        patientHomePage.getDatePickerInput().click();
        patientHomePage.getDatePickerInput().sendKeys("2020-02-10");

        patientHomePage.getBtnSearch().click();
        patientHomePage.ensureIsVisibleDivListClinics();

        Thread.sleep(1000);
        List<WebElement> rows = patientHomePage.getDivListClinics().findElements(By.className("clinics"));

        Assertions.assertEquals(1, rows.size());

    }

    @Test
    public void testPatientSearchingClinicsReservingExamSuccess() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("pera");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        patientHomePage.ensureIsClickableBtnListOfClinics();
        patientHomePage.getBtnListOfClinics().click();

        patientHomePage.ensureIsVisibleShowListOfClinics();

        patientHomePage.ensureIsClickableSelectExamType();
        patientHomePage.getSelectExamType().click();

        patientHomePage.ensureIsClickableItemId();
        patientHomePage.getItemId().click();

        patientHomePage.getDatePickerInput().click();
        patientHomePage.getDatePickerInput().sendKeys("2020-02-10");

        patientHomePage.getBtnSearch().click();
        patientHomePage.ensureIsVisibleDivListClinics();

        patientHomePage.ensureIsVisibleBtnClinic();
        patientHomePage.getBtnClinic().click();
        patientHomePage.ensureIsVisibleTableDoctor();
        patientHomePage.ensureIsClickableRowDoctor();
        patientHomePage.getRowDoctor().click();

        patientHomePage.ensureIsVisibleTableAppointments();
        Thread.sleep(1000);
        List<WebElement> rowsBefore = patientHomePage.getTableAppointments().findElements(By.name("counting-appointments"));
        List<WebElement> buttons = patientHomePage.getTableAppointments().findElements(By.name("appointment-buttons"));
        buttons.get(0).click();
        Thread.sleep(7000);
        patientHomePage.ensureIsVisibleTableDoctor();
        patientHomePage.ensureIsClickableBtnReset();
        patientHomePage.getBtnReset().click();

        patientHomePage.ensureIsVisibleTableDoctor();
        patientHomePage.ensureIsClickableRowDoctor();
        patientHomePage.getRowDoctor().click();
        patientHomePage.ensureIsVisibleTableAppointments();
        Thread.sleep(1000);
        List<WebElement> rows = patientHomePage.getTableAppointments().findElements(By.name("counting-appointments"));

        patientHomePage.ensureIsClickablePatientLogout();
        patientHomePage.getBtnPatientLogout().click();

        patientHomePage.ensureIsNotVisiblePatientLogout();

        Assertions.assertEquals(rowsBefore.size() - 1, rows.size());



    }

    @Test
    public void testPatientSearchingClinicsReservingFastExam() throws InterruptedException {

        driver.navigate().to(baseUrl + "/login");
        loginPage.ensureIsDisplayedEmail();

        loginPage.getEmail().sendKeys("isa2019pacijent@outlook.com");
        loginPage.getPassword().sendKeys("pera");

        loginPage.getLoginBtn().click();
        loginPage.ensureIsNotVisibleLoginBtn();

        patientHomePage.ensureIsClickableBtnListOfClinics();
        patientHomePage.getBtnListOfClinics().click();

        patientHomePage.ensureIsVisibleShowListOfClinics();

        patientHomePage.ensureIsClickableSelectExamType();
        patientHomePage.getSelectExamType().click();

        patientHomePage.ensureIsClickableItemId();
        patientHomePage.getItemId().click();

        patientHomePage.getDatePickerInput().click();
        patientHomePage.getDatePickerInput().sendKeys("2020-02-10");

        patientHomePage.getBtnSearch().click();
        patientHomePage.ensureIsVisibleDivListClinics();

        List<WebElement> buttons = patientHomePage.getDivListClinics().findElements(By.name("btn-clinic-profile"));
        buttons.get(0).click();

        patientHomePage.ensureIsClickableBtnMenu();
        patientHomePage.getBtnMenu().click();
        Thread.sleep(500);
        patientHomePage.ensureIsClickableBtnFastExam();
        patientHomePage.getBtnFastExam().click();
        Thread.sleep(1000);
        patientHomePage.ensureIsVisibleTableFastExams();
        List<WebElement> rowsBefore = patientHomePage.getTableFastExams().findElements(By.name("counting-fast-exams"));
        List<WebElement> buttons2 = patientHomePage.getTableFastExams().findElements(By.name("btn-reserve-fast-exam"));

        buttons2.get(0).click();
        patientHomePage.ensureIsVisibleTableFastExams();
        Thread.sleep(5000);
        List<WebElement> rows = patientHomePage.getTableFastExams().findElements(By.name("counting-fast-exams"));

        Assertions.assertEquals(rowsBefore.size() - 1, rows.size());



    }



}
