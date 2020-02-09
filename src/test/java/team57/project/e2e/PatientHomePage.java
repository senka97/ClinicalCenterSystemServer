package team57.project.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientHomePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"btn-list-of-clinics\"]")
    private WebElement btnListOfClinics;

    @FindBy(xpath = "//*[@id=\"show-list-clinics\"]")
    private WebElement showListOfClinics;

    @FindBy(xpath = "//*[@id=\"select-exam-type\"]")
    private WebElement selectExamType;

    @FindBy(xpath = "//*[@id=\"1\"]")
    private WebElement itemId;

    @FindBy(xpath = "//*[@id=\"date-picker-input\"]")
    private WebElement datePickerInput;

    @FindBy(xpath = "//*[@id=\"btn-search\"]")
    private WebElement btnSearch;

    @FindBy(xpath = "//*[@id=\"div-list-clinics\"]")
    private WebElement divListClinics;

    @FindBy(xpath = "//*[@id=\"div-no-clinics\"]")
    private WebElement divNoClinics;

    @FindBy(xpath = "//*[@id=\"1\"]")
    private WebElement btnClinic;

    @FindBy(xpath = "//*[@id=\"myTable\"]")
    private WebElement tableDoctor;

    @FindBy(xpath = "//*[@id=\"3\"]")
    private WebElement rowDoctor;

    @FindBy(xpath = "//*[@id=\"table-appointments\"]")
    private WebElement tableAppointments;

    @FindBy(xpath = "//*[@id=\"4\"]")
    private WebElement appointmentId;

    @FindBy(xpath = "//*[@id=\"btn-patient-logout\"]")
    private WebElement btnPatientLogout;

    @FindBy(xpath = "//*[@id=\"btn-reset\"]")
    private WebElement btnReset;

    public PatientHomePage()
    {

    }

    public PatientHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsClickableBtnListOfClinics() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnListOfClinics));
    }

    public void ensureIsVisibleShowListOfClinics()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(showListOfClinics));
    }

    public void ensureIsClickableSelectExamType() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(selectExamType));
    }

    public void ensureIsClickableItemId() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(itemId));
    }

    public void ensureIsVisibleDivListClinics() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(divListClinics));
    }

    public void ensureIsVisibleDivNoClinis()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(divNoClinics));
    }

    public void ensureIsVisibleBtnClinic()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(btnClinic));
    }

    public void ensureIsVisibleTableDoctor()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(tableDoctor));
    }

    public void ensureIsClickableRowDoctor()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(rowDoctor));
    }

    public void ensureIsClickableBtnReset()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(btnReset));
    }

    public void ensureIsVisibleTableAppointments()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(tableAppointments));
    }

    public void ensureIsClickableAppointment()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(appointmentId));
    }

    public void ensureIsNotVisibleTableAppointments()
    {
        (new WebDriverWait(driver,20)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-appointments")));
    }

    public void ensureIsClickablePatientLogout()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(btnPatientLogout));
    }

    public void ensureIsNotVisiblePatientLogout()
    {
        (new WebDriverWait(driver,10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("btn-patient-logout")));
    }

    public WebElement getBtnListOfClinics() {
        return btnListOfClinics;
    }

    public WebElement getShowListOfClinics() {
        return showListOfClinics;
    }

    public WebElement getSelectExamType() {
        return selectExamType;
    }

    public WebElement getItemId() {
        return itemId;
    }

    public WebElement getDatePickerInput() {
        return datePickerInput;
    }

    public WebElement getBtnSearch() {
        return btnSearch;
    }

    public WebElement getDivListClinics() {
        return divListClinics;
    }

    public WebElement getDivNoClinics() {
        return divNoClinics;
    }

    public WebElement getBtnClinic() {
        return btnClinic;
    }

    public WebElement getRowDoctor() {
        return rowDoctor;
    }

    public WebElement getTableAppointments() {
        return tableAppointments;
    }

    public WebElement getAppointmentId() {
        return appointmentId;
    }

    public WebElement getBtnPatientLogout() {
        return btnPatientLogout;
    }

    public WebElement getTableDoctor() {
        return tableDoctor;
    }

    public WebElement getBtnReset() {
        return btnReset;
    }
}
