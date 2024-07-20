package org.example.selenium;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumConfig {
    static WebDriver driver;
    static Config youtubeConfig;
    static Config rutubeConfig;
    static Config wildberriesConfig;

    private static final Logger log = LoggerFactory.getLogger(SeleniumConfig.class);

    @BeforeAll
    public static void setUp() {

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        log.info("драйвер создан");
    }

    @AfterAll
    public static void quit(){
        //driver.quit();
        log.info("браузер закрыт");
    }

}
