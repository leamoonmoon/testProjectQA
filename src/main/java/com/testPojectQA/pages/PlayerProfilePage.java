package com.testPojectQA.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class PlayerProfilePage {
    private WebDriver driver;

    public PlayerProfilePage(WebDriver driver) {
        this.driver = driver;
    }
    // private By last5GamesTable = By.xpath("//h2[contains(text(), 'Last 5 Games')]/following-sibling::div//table");

    // retrieve the average of 3PM
    public double getLast5Games3PM() {
        // Find the table - XPath
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        //List<WebElement> rows = table.findElements(By.tagName("tr"));

        double total3PM = 0;
        int gameCount = 0;

        for (WebElement row : rows) {
            //System.out.println(row.getText());
            try {
                //List<WebElement> columns = row.findElements(By.tagName("td"));
                WebElement threePointerElement = row.findElement(By.xpath(".//td[9]"));
                String threePointerText = threePointerElement.getText();
                total3PM += Double.parseDouble(threePointerText);
                gameCount++;
            } catch (Exception e) {
                System.err.println("Error retrieving 3 pointer per game for this row: " + e.getMessage());
            }
        }
        return gameCount > 0 ? total3PM / gameCount : 0;
    }
}
