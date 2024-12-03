package com.testProjectQA.tests;

import com.testProjectQA.utils.SportsDataAPI;
import com.testPojectQA.pages.PlayerProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Map;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class NBAPlayerTests {
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(NBAPlayerTests.class);
    private static final String BASE_URL = "https://www.nba.com/player";

    @BeforeEach
    public void setup() {
        //  manage the ChromeDriver version
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // configuration for CI/CD
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage","--disable-gpu");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testPlayer3PM() {

        ArrayList<String> failedPlayers = new ArrayList<>();
        Map<String, Integer> playerMap = SportsDataAPI.getPlayerNameToIdMap();

        for (Map.Entry<String, Integer> entry : playerMap.entrySet()) {
            String playerName = entry.getKey();
            int nbaDotComId = entry.getValue();

            String profileUrl = BASE_URL + "/" + nbaDotComId  + "/" + playerName;
            logger.info("Processing player: {}", playerName);
            driver.get(profileUrl);
           // System.out.println(driver.getPageSource());

            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody")));
            } catch (Exception e) {
                logger.warn("Timed out waiting for page to load: {}", playerName);
                failedPlayers.add(playerName);
                continue;
            }

            PlayerProfilePage playerPage = new PlayerProfilePage(driver);
            double avg3PM = playerPage.getLast5Games3PM();
            logger.info("Average 3PM for {} in last 5 games: {}", playerName, avg3PM);

            //  mark the player as failed
            if (avg3PM < 1) {
                failedPlayers.add(playerName);
            }

        }
        // reporting failed players
        if (!failedPlayers.isEmpty()) {
            String failedPlayersList = String.join(", ", failedPlayers);
            fail("The following players have less than 1 3PM average in the last 5 games: " + failedPlayersList);
        }
    }
}
