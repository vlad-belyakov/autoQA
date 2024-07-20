package org.example.selenium;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class YoutubeTest extends SeleniumConfig{

    private final Logger LOG = LoggerFactory.getLogger(YoutubeTest.class);

    @FindBy(xpath = "//input[@name='search_query']")
    private WebElement searchQuery;
    @FindBy(id = "search-icon-legacy")
    private WebElement searchIcon;
    @FindBy(css = "button[class='ytp-play-button ytp-button']")
    private WebElement playButton;

    public YoutubeTest(){
        youtubeConfig = ConfigFactory.parseResources("youtube.conf").withFallback(rutubeConfig);
        PageFactory.initElements(driver, this);
    }

    @DisplayName("позитивный тест на воспроизведение видео в сервисе youtube")
    @ParameterizedTest()
    @Order(1)
    @CsvSource({"https://www.youtube.com/watch?v=4xHesEK0suM, видео успешно воспроизведено"})
    public void searchAndPlayVideoTest(String videoURL, String log) {
        LOG.info("метод запуска видео на ютуб запущен");
        driver.get(youtubeConfig.getString("youtube.main_page_url"));
        searchQuery.sendKeys(youtubeConfig.getString("rutube.search_text"));
        searchIcon.click();
        driver.get(videoURL);
        playButton.click();
        LOG.info(log);
    }

    @Test
    @Order(2)
    public void returnToMainPageTest(){
        LOG.info("метод возвращения запущен");
        driver.navigate().back();
        driver.navigate().forward();
        for (short i = 0; i < 2; i++){
            driver.navigate().back();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.error("поток прерван из-за ошибки");
            throw new RuntimeException(e);
        }
    }

    @Disabled
    @Test
    public void toDeleteTest(){
        //иммитация метода на удаление
    }

}
