package pages;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Получаем экземпляр теста
        Object testInstance = context.getRequiredTestInstance();

        // Проверяем, что это наш BaseTest
        if (testInstance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) testInstance;
            WebDriver driver = baseTest.driver;

            // Если драйвер жив — делаем скриншот
            if (driver != null) {
                takeScreenshot(driver);
            } else {
                System.err.println("⚠️ Драйвер null, скриншот не сохранен!");
            }
        }
    }

    @Attachment(value = "Скриншот при падении теста", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("❌ Ошибка создания скриншота: " + e.getMessage());
            return new byte[0];
        }
    }
}