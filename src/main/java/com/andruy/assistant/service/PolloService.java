package com.andruy.assistant.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.andruy.assistant.model.PushNotification;

@Service
public class PolloService {
    Logger logger = LoggerFactory.getLogger(PolloService.class);
    private final String ADDRESS = "https://www.pollolistens.com/";
    private final String NEXT = "nextPageLink";
    private final int LONG_HALT = 3000;
    private final int SHORT_HALT = 500;
    private List<WebElement> elements;
    private String response = "";
    private WebElement element;
    private WebDriver driver;
    private Actions actions;
    @Autowired
    private PushNotificationService pushNotificationService;

    @Async
    public CompletableFuture<Void> pollo(Map<String, String> payload) {
        List<String> body = List.of(
            payload.get("code").substring(0, 4),
            payload.get("code").substring(4, 8),
            payload.get("code").substring(8, 12),
            payload.get("code").substring(12, 16),
            payload.get("meal"),
            payload.get("visit")
        );

        logger.trace("Pollo code: " + payload.get("code"));
        logger.trace("Pollo meal: " + payload.get("meal"));
        logger.trace("Pollo visit: " + payload.get("visit"));

        try {
            int page = 0;
            int i = 0;
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            driver.get(ADDRESS);
            Thread.sleep(LONG_HALT);

            // Select language
            element = driver.findElement(By.id("option_1568944_667416"));
            actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Enter code
            driver.findElement(By.id("promptInput_665451_0")).sendKeys(body.get(i++));
            driver.findElement(By.id("promptInput_665451_1")).sendKeys(body.get(i++));
            driver.findElement(By.id("promptInput_665451_2")).sendKeys(body.get(i++));
            driver.findElement(By.id("promptInput_665451_3")).sendKeys(body.get(i++));
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Rate visit
            elements = driver.findElements(By.className("rating"));
            if (elements.isEmpty()) {
                logger.warn("No ratings found");
            }
            actions = new Actions(driver);
            actions.moveToElement(elements.get(elements.size() - 1)).click().perform();
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Select meal
            elements = driver.findElements(By.className("booleanText"));
            for (WebElement e : elements) {
                element = e.findElement(By.className("text")).findElement(By.className("ng-binding"));
                if (element.getText().contains(body.get(i))) {
                    i++;
                    break;
                }
            }
            actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // More ratings
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Long viewportHeight = (Long) js.executeScript("return window.innerHeight;");

            elements = driver.findElements(By.className("rating"));
            for (WebElement e : elements) {
                if (e.findElement(By.tagName("div")).getText().equals("5")) {
                    actions = new Actions(driver);
                    actions.moveToElement(e).click().perform();
                    long scrollHeight = viewportHeight / 5;
                    js.executeScript("window.scrollBy(0, arguments[0]);", scrollHeight);
                    Thread.sleep(SHORT_HALT);
                }
            }
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Visit type
            element = driver.findElement(By.id(body.get(i).contains("Dine") ? "option_1547526_658917" : "option_1547528_658917"));
            actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Age and gender
            element = driver.findElement(By.id("option_1576812_670283"));
            actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            element = driver.findElement(By.id("option_1576818_670286"));
            actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            Thread.sleep(SHORT_HALT);
            driver.findElement(By.id(NEXT)).click();
            logger.trace("Leaving page " + page++);
            Thread.sleep(LONG_HALT);

            // Redeem
            elements = driver.findElements(By.className("text"));
            element = elements.get(0).findElement(By.tagName("label")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.tagName("div")).get(3);
            response = element.getText();
            int status = pushNotificationService.push(new PushNotification("Pollo reward code", response));
            logger.trace("Push notification status: " + status + " with reward code " + response);

            driver.close();
            driver.quit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            driver.close();
            driver.quit();
            response = "Something went wrong";
        }

        return CompletableFuture.completedFuture(null);
    }
}
