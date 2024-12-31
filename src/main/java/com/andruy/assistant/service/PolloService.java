package com.andruy.assistant.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.andruy.assistant.model.PushNotification;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import oracle.net.aso.c;

@Service
public class PolloService {
    Logger logger = LoggerFactory.getLogger(PolloService.class);
    private final String ADDRESS = "https://www.pollolistens.com/";
    private final String MOVING_ON = "Leaving page ";
    private final String NEXT = "#nextPageLink";
    private String response = "";
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

        try (Playwright playwright = Playwright.create()) {
            int iterator = 0;
            int pageNumber = 0;

            // Launch a browser (Chromium, Firefox, Webkit)
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

            // Open a new browser context and page
            Page page = browser.newPage();

            // Navigate to a website
            page.navigate(ADDRESS);

            // Select language
            page.click("//label[@for='option_1568944_667416']");
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Enter code
            page.fill("#promptInput_665451_0", body.get(iterator++));
            page.fill("#promptInput_665451_1", body.get(iterator++));
            page.fill("#promptInput_665451_2", body.get(iterator++));
            page.fill("#promptInput_665451_3", body.get(iterator++));
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Rate visit
            page.locator(".rating").last().click();
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Select meal
            page.click("//*[@id=\"prompt_670287\"]/label/div");
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // More ratings
            Locator elements = page.locator(".rating");
            for (int i = 0; i < elements.count(); i++) {
                if (elements.nth(i).locator("div").textContent().equals("5")) {
                    elements.nth(i).click();
                    page.evaluate("window.scrollBy(0, window.innerHeight / 5)");
                }
            }
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Visit type
            page.click(body.get(iterator).contains("Dine") ? "#option_1547526_658917" : "#option_1547528_658917");
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Age and gender
            page.click("#option_1576812_670283");
            page.click("#option_1576818_670286");
            page.click(NEXT);
            logger.trace(MOVING_ON + pageNumber++);

            // Redeem
            page.locator(".text").first().locator("label");

            // Close the browser
            browser.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            response = "Something went wrong";
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> oldPollo(Map<String, String> payload) {
        try {
            ChromeOptions options = new ChromeOptions();
            WebDriver driver = new ChromeDriver(options);

            // Redeem
            List<WebElement> elements = driver.findElements(By.className("text"));
            WebElement element = elements.get(0).findElement(By.tagName("label")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.tagName("div")).get(3);
            response = element.getText();
            int status = pushNotificationService.push(new PushNotification("Pollo reward code", response));
            logger.trace("Push notification status: " + status + " with reward code " + response);

            driver.close();
            driver.quit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            response = "Something went wrong";
        }

        return CompletableFuture.completedFuture(null);
    }
}
