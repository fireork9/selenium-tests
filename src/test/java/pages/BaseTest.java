package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@ExtendWith(TestListener.class)   // ← для скриншотов при падении
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Проверяем, запущены ли тесты в Docker
        String headless = System.getenv("CHROME_HEADLESS");
        if ("true".equals(headless)) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu"); // ОБЯЗАТЕЛЬНЫЙ ФЛАГ ДЛЯ ДЕБИАН
            options.addArguments("--window-size=1920,1080");

            options.setBinary("/usr/bin/chromium");
        } else {
            // Если запускаем локально на ПК — скачиваем драйвер через WebDriverManager автоматически
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        }

        // Создаем драйвер ОДИН раз
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://saucedemo.com");

        wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}