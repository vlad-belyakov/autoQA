package org.example.selenium;

import com.typesafe.config.ConfigFactory;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WildberriesTest extends SeleniumConfig{

    private static final Logger LOG = LoggerFactory.getLogger(WildberriesTest.class);

    private WebDriverWait wait;
    @FindBy(xpath = "//input[@id='searchInput']")
    private WebElement searchQuery;

    private List<WebElement> goodsList;

    public WildberriesTest(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wildberriesConfig = ConfigFactory.parseResources("ruservices.conf");
        PageFactory.initElements(driver, this);
    }

    @Order(1)
    @Test
    public void searchAndCheckGoodsTest() {
        LOG.info("метод проверки списка товаров запущен");
        String expectedValue = "Миндальная";
        List<String> wrongValues = new ArrayList<>();
        driver.get(wildberriesConfig.getString("wildberries.main_page_url"));
        searchQuery.sendKeys(wildberriesConfig.getString("wildberries.search_text"));
        searchQuery.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'product-card__wrapper']/a")));
        goodsList = driver.findElements(
                By.xpath("//div[@class = 'product-card__wrapper']/a"));
        for (WebElement element: goodsList){
            String goodsName = element.getAttribute("aria-label");
            if (!(goodsName.contains(expectedValue) || goodsName.contains(expectedValue.toLowerCase()))) {
                wrongValues.add(goodsName);
                LOG.error(goodsName);
            }
        }

        Assertions.assertNull(wrongValues.get(0));
    }
}
