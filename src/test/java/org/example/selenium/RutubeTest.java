package org.example.selenium;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RutubeTest extends SeleniumConfig{

    private final Logger LOG = LoggerFactory.getLogger(RutubeTest.class);
    @FindBys({
            @FindBy(tagName = "div"),
            @FindBy(linkText = "Фильмы")
    })
    private WebElement filmRef;

    public RutubeTest(){
        rutubeConfig = ConfigFactory.parseResources("ruservices.conf");
        PageFactory.initElements(driver, this);
    }

    @Test
    @Order(1)
    public void changeAndRefreshFilmPage(){
        driver.get(rutubeConfig.getString("rutube.main_page_url"));
        driver.get(filmRef.getAttribute("href"));
        driver.navigate().refresh();
        driver.navigate().back();
        Assertions.assertEquals(rutubeConfig.getString("rutube.main_page_url"), driver.getCurrentUrl());
    }

    @Test
    @Order(2)
    public void checkCinemaReviewsTitle(){
        String title = driver.findElement(By.xpath("//a[@title = 'Кинообзоры']")).getText();
        Assertions.assertEquals(title, "Кинообзоры");
    }

}
