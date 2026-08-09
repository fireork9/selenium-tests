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
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Проверяем, запущены ли тесты в Docker
        String headless = System.getenv("CHROME_HEADLESS");
        if ("true".equals(headless)) {
            // Указываем точный путь к установленному в Linux драйверу вместо скачивания
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            options.addArguments("--headless=new"); // Используем современныйheadless-режим
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.setBinary("/usr/bin/chromium"); // Принудительно указываем на сам браузер
        } else {
            // Если запускаем локально на ПК — скачиваем драйвер через WebDriverManager автоматически
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        }

        // Создаем драйвер ОДИН раз
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://saucedemo.com");

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