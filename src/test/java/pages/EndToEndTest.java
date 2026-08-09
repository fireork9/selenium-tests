package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class EndToEndTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() throws Exception {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();

        String headless = System.getenv("CHROME_HEADLESS");
        if ("true".equals(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            // Безопасный способ создания URL для Java 21
            driver = new org.openqa.selenium.remote.RemoteWebDriver(
                    java.net.URI.create("http://chrome-server:4444/wd/hub").toURL(), options
            );
        } else {
            // Обычный запуск локально на вашем ПК
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.get("https://saucedemo.com");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void endToEnd() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        CartPage cartPage = inventoryPage
                .addBackpackToCart()
                .goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        String finalText = checkoutPage
                .fillForm("Serega", "Melnik", "66669")
                .finishOrder()
                .getSuccessMessage();
        assertEquals("Thank you for your order!", finalText, "Ошибка покупка не совершена");
    }

    @AfterEach
    public void tearDown() {
            driver.quit();

    }
}