package com.wikipedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WikipediaTest01 extends BaseTest {

    @BeforeEach
    public void setUp() {
        driver.get("https://en.wikipedia.org/wiki/Mercedes-Benz_Sprinter");
    }

    @Test
    public void verifyPageTitle() {
        WebElement h1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));
        assertEquals("Mercedes-Benz Sprinter", h1.getText(), "H1 title should match");
        System.out.println("Page title verified: " + h1.getText());
    }

    @Test
    public void verifyPageStructure() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mw-logo-wordmark")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='search']")));
        System.out.println("Page structure verified correctly.");
    }

    @Test
    public void verifyContentSections() {
        List<String> sections = Arrays.asList(
                "First generation", "Second generation", "Third generation");
        for (String section : sections) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'" + section + "')]")));
            System.out.println("Section verified: " + section);
        }
        System.out.println("All content sections verified.");
    }

    @Test
    public void verifyInfoboxContainsBrand() {
        WebElement infobox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".infobox")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true)", infobox);
        assertTrue(infobox.getText().contains("Mercedes-Benz"),
                "Infobox should contain the brand name");
        System.out.println("Infobox brand name verified.");
    }

    @Test
    public void verifyFooterLinks() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight)");
        List<String> links = Arrays.asList(
                "Privacy policy", "About Wikipedia", "Disclaimers");
        for (String link : links) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id='footer']//a[contains(.,'" + link + "')]")));
            System.out.println("Footer link verified: " + link);
        }
        System.out.println("Footer links verified.");
    }
}