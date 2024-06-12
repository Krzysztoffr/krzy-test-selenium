package com.krzytest;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class WikipediaCssTest {

    private static final String URL = "https://www.wikipedia.org/";

    private WebDriver driver;

    @BeforeEach
    public void be() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=pl-Pl");
        options.addArguments("--start-maximized");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void ae() {
//        driver.close();
        driver.quit();
    }

    @Test
    public void openBrowser() {

        driver.get(URL);
       // var title = driver.getTitle();

        var element1 = driver.findElements(By.xpath("//*[@id=\"js-link-box\"]/strong"));

                for (WebElement el : element1) {
            System.out.println(el.getText());
        }
    }
    @Test
    public void checkLanguages() throws InterruptedException {
        var expected = List.of("Polski", "English",  "日本語", "Русский", "Deutsch", "Español",  "Français", "Italiano", "中文", "فارسی");
        var expected2 = new ArrayList<>(expected);
        expected2.forEach(System.out::print);
        System.out.println("\n");
        driver.get(URL);

        var elements= driver.findElements(By.cssSelector(".central-featured-lang strong "));  //driver.findElements(By.cssSelector(".link-box"));
        Thread.sleep(Duration.ofSeconds(2)); // java-1

        var languageList = elements.stream()
                .map(WebElement::getText)
                .toList();

        //languageList.forEach(System.out::print);
        for(WebElement el : elements){System.out.print(el.getText());}
        System.out.println("\n");

        for (String lan : languageList) {
            var first = expected2.get(0);
            if (!first.equals(lan)) {
                System.out.println("różnica kolejności");
                throw new RuntimeException("error-assert");
            }
            expected2.remove(0);
        }

        if (!expected2.isEmpty()) {
            System.out.println("nie-pusta");
            throw new RuntimeException("error-assert");
        }
        System.out.println("sukces");

    }

    private String getLanguage(@NotNull WebElement webElement) {
        return webElement.findElement(By.tagName("strong")).getText();
    }

    private @NotNull String getLanguageText(@NotNull WebElement webElement) {
        try {
            String bdiText = webElement.findElement(By.tagName("bdi")).getText();
            String spanText = webElement.findElement(By.tagName("span")).getText();
            return bdiText + " " + spanText;
        } catch (NoSuchElementException e) {
            System.out.println("Nie znaleziono elementu: " + e.getMessage());
            return "Element not found";
        }
    }


}
