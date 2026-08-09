import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FirstTest extends BaseTest {




        @Test
        public void cheakTitle() throws InterruptedException {
            Thread.sleep(2000);
            String expetedTitle = "Swag Labs";
            String actualTitle = driver.getTitle();
            assertEquals(expetedTitle, actualTitle, "Заголовок страницы не верный");

        }

        @Test
        public void login() throws InterruptedException {
            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='user-name']")).sendKeys("standard_user");

            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='password']")).sendKeys("secret_sauce");

            driver.findElement(By.xpath("//input[@class='submit-button btn_action' and @id='login-button']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contents_wrapper")));

            String curretAlet = driver.getCurrentUrl();
            assertEquals("https://www.saucedemo.com/inventory.html", curretAlet, "Логин не удался!");
        }

        @Test
        public void loginBlock() throws InterruptedException {
            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='user-name']")).sendKeys("locked_out_user");

            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='password']")).sendKeys("secret_sauce");

            driver.findElement(By.xpath("//input[@class='submit-button btn_action' and @id='login-button']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Epic sadface: Sorry, this user has been locked out.']")));

            String erorNegativ = driver.findElement(By.xpath("//h3[text()='Epic sadface: Sorry, this user has been locked out.']")).getText();
            assertEquals("Epic sadface: Sorry, this user has been locked out.", erorNegativ, "Тест неверный");

        }

        @Test
        public void sortItemOnPrice() throws InterruptedException {
            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='user-name']")).sendKeys("standard_user");

            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='password']")).sendKeys("secret_sauce");

            driver.findElement(By.xpath("//input[@class='submit-button btn_action' and @id='login-button']")).click();

            Thread.sleep(2000);

            driver.findElement(By.xpath("//select[@class='product_sort_container']")).click();
            driver.findElement(By.xpath("//option[text()='Price (low to high)']")).click();

            List<WebElement> items = driver.findElements(By.className("inventory_item_price"));
            Double price = Double.parseDouble(items.get(0).getText().replace("$", ""));
            Double price1 = Double.parseDouble(items.get(1).getText().replace("$", ""));
            Assertions.assertTrue(price <= price1, "Ошибка, цена второго товара меньше.");

        }

        @Test
        public void countItemsOnWeb() throws InterruptedException {
            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='user-name']")).sendKeys("standard_user");

            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='password']")).sendKeys("secret_sauce");

            driver.findElement(By.xpath("//input[@class='submit-button btn_action' and @id='login-button']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inventory_item']")));

            List<WebElement> countElement = driver.findElements(By.xpath("//div[@class='inventory_item']"));
            assertEquals(6, countElement.size(), "Количество элемент не равно 6");
        }

        @Test
        public void endToEnd() {
            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='user-name']")).sendKeys("standard_user");

            driver.findElement(By.xpath("//input[@class='input_error form_input' and @id='password']")).sendKeys("secret_sauce");

            driver.findElement(By.xpath("//input[@class='submit-button btn_action' and @id='login-button']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contents_wrapper")));

            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page_wrapper")));

            driver.findElement(By.id("checkout")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("checkout_info_container")));

            driver.findElement(By.id("first-name")).sendKeys("Serega");
            driver.findElement(By.id("last-name")).sendKeys("Melnik");
            driver.findElement(By.id("postal-code")).sendKeys("66669");
            driver.findElement(By.id("continue")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

            driver.findElement(By.id("finish")).click();

            String finalText = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();

            assertEquals("Thank you for your order!", finalText, "Ошибка покупка не совершена");


        }


    }
