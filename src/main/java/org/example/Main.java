package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ChromeOptions chromeOptions = new ChromeOptions();


        WebDriver driver = new ChromeDriver(chromeOptions);


        driver.get("https://google.com");

        // 2. Создаем таймер ожидания на 10 секунд
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 3. Ждем, пока строка поиска гарантированно появится на экране
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("textarea")));


        // 4. Вводим текст "тест"
        input.sendKeys("тест");

        // 5. Имитируем нажатие клавиши Enter на клавиатуре прямо в строку ввода
        input.sendKeys(Keys.ENTER);

    }
}