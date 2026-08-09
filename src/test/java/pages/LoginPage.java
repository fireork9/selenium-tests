package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы (твои XPath)
    private By usernameField = By.xpath("//input[@class='input_error form_input' and @id='user-name']");
    private By passwordField = By.xpath("//input[@class='input_error form_input' and @id='password']");
    private By loginButton = By.xpath("//input[@class='submit-button btn_action' and @id='login-button']");
    private By errorMessage = By.xpath("//h3[text()='Epic sadface: Sorry, this user has been locked out.']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Метод для успешного входа
    public InventoryPage login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return new InventoryPage(driver);
    }

    // Метод для проверки ошибки (для locked_out_user)
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }
}

