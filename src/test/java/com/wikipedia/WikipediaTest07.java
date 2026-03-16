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

public class WikipediaTest07 extends BaseTest {

    @BeforeEach
    public void setUp() {
        driver.get("https://en.wikipedia.org/wiki/Peavey_Electronics");
    }

    @Test
    public void verifyPageTitle() {
        WebElement h1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));
        assertEquals("Peavey Electronics", h1.getText(), "H1 title should match");
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
        List<String> sections = Arrays.asList("History", "Products", "References");
        for (String section : sections) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'" + section + "')]")));
            System.out.println("Section verified: " + section);
        }
        System.out.println("All content sections verified.");
    }

@Test
public void verifySidebarNavigationMenu() {
    WebElement checkbox = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                    By.id("vector-main-menu-dropdown-checkbox")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    List<String> menuLinks = Arrays.asList(
            "Main page", "Random article", "About Wikipedia");
    for (String link : menuLinks) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='vector-main-menu']//a[contains(.,'" + link + "')]")));
        System.out.println("Menu link verified: " + link);
    }
    System.out.println("Sidebar navigation verified.");
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