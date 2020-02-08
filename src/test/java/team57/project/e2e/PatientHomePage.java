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

    @FindBy(xpath = "//*[@id=\"show-list-of-clinics\"]")
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
}
