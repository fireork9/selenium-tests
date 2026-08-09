package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private By cartLink = By.xpath("//a[@class='shopping_cart_link']");
    private By sortDropdown = By.xpath("//select[@class='product_sort_container']");
    private By priceLowToHigh = By.xpath("//option[text()='Price (low to high)']");
    private By itemPrices = By.className("inventory_item_price");
    private By inventoryItems = By.xpath("//div[@class='inventory_item']");
    private By addToCartBackpack = By.id("add-to-cart-sauce-labs-backpack");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Добавить товар в корзину
    public InventoryPage addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBackpack)).click();
        return this;
    }

    // Перейти в корзину
    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }

    // Сортировка по цене (low to high)
    public InventoryPage sortByPriceLowToHigh() {
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)).click();
        driver.findElement(priceLowToHigh).click();
        return this;
    }

    // Получить список цен
    public List<Double> getItemPrices() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices));
        List<WebElement> priceElements = driver.findElements(itemPrices);

        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String text = element.getText();
            String withoutDollar = text.replace("$", "");
            double price = Double.parseDouble(withoutDollar);
            prices.add(price);
        }
        return prices;
    }

    // Получить количество товаров на странице
    public int getInventoryItemCount() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems));
        return driver.findElements(inventoryItems).size();
    }
}