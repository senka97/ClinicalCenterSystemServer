package team57.project.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClinicAdminHomePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"btn-exam-room\"]")
    private WebElement btnExamRoom;

    @FindBy(xpath = "//*[@id=\"table-exam-requests\"]")
    private WebElement tableExamRequests;

   /* @FindBy(xpath = "//*[@id=\"6\"]")
    private WebElement btnExamRequest;*/

    @FindBy(xpath = "//*[@id=\"table-exam-rooms-success\"]")
    private WebElement tableExamRoomSuccess;

   /* @FindBy(xpath = "//*[@id=\"btn-reserve\"]")
    private WebElement btnReserve;*/

    @FindBy(xpath = "//*[@id=\"btn-reject\"]")
    private WebElement btnReject;

    @FindBy(xpath = "//*[@id=\"btn-ok\"]")
    private WebElement btnOk;



    public ClinicAdminHomePage()
    {

    }

    public void ensureIsClickableBtnExamRoom() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnExamRoom));
    }

    public void ensureIsVisibleTableExamRequests() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(tableExamRequests));
    }

   /* public void ensureIsClickableBtnExamRequest() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnExamRequest));
    }*/

    public void ensureIsVisibleTableExamRoomSuccess() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(tableExamRoomSuccess));
    }

  /*  public void ensureIsClickableBtnReserve() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnReserve));
    }*/

    public void ensureIsClickableBtnReject() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnReject));
    }

    public void ensureIsClickableBtnOk() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnOk));
    }



    public ClinicAdminHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBtnExamRoom() {
        return btnExamRoom;
    }

    public WebElement getTableExamRequests() {
        return tableExamRequests;
    }

   /* public WebElement getBtnExamRequest() {
        return btnExamRequest;
    }*/

    public WebElement getTableExamRoomSuccess() {
        return tableExamRoomSuccess;
    }

   /* public WebElement getBtnReserve() {
        return btnReserve;
    }*/

    public WebElement getBtnReject() {
        return btnReject;
    }

    public WebElement getBtnOk() {
        return btnOk;
    }


}
