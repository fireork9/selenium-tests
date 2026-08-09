package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class EndToEndTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
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