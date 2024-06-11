package com.krzytest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/*
Praca domowa zrób 4 waity dla przykładu z
https://the-internet.herokuapp.com/dynamic_loading/2

isDisplayed()

https://hackernoon.com/recommended-websites-to-practice-selenium-and-test-automation
 */
public class WaitsTest {

    private final String URL = "https://the-internet.herokuapp.com/dynamic_loading/1";

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
    public void t1() throws InterruptedException {
        driver.get(URL);

        var buttonDiv = driver.findElement(By.id("start"));
        var button = buttonDiv.findElement(By.tagName("button"));
        button.click();

        System.out.println("Przed");
        Thread.sleep(Duration.ofSeconds(15)); // java-1
        System.out.println("Po");

        var element = driver.findElement(By.id("finish"));
        var textElement = element.findElement(By.tagName("h4"));

        System.out.println(textElement.getText());


        Assertions.assertThat(element).isNotNull();
        Assertions.assertThat(textElement.getText()).isEqualTo("Hello World!");
    }

    @Test
    public void t2() throws InterruptedException {
        driver.get(URL);

        var buttonDiv = driver.findElement(By.id("start"));
        var button = buttonDiv.findElement(By.tagName("button"));
        button.click();

        System.out.println("Przed");

        var until = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);

        boolean success = false;
        String text = null;
        int i = 0;
        while (System.currentTimeMillis() < until) {
            i++;
            System.out.println("Uruchomienie: " + i + " " + LocalDateTime.now());
            var element = driver.findElement(By.id("finish"));
            var textElement = element.findElement(By.tagName("h4"));
            text = textElement.getText();

            if (text != null && !text.isEmpty()) {
                System.out.println("znalazłem");
                success = true;
                break;
            }

            Thread.sleep(Duration.ofMillis(500));
        }

        if (!success) {
            System.out.println("Not visible!");
            throw new RuntimeException("Error");
        }

        System.out.println("Po");

        Assertions.assertThat(text).isEqualTo("Hello World!");
    }

    @Test
    public void t3() {
        driver.get(URL);

        var buttonDiv = driver.findElement(By.id("start"));
        var button = buttonDiv.findElement(By.tagName("button"));
        button.click();

        Wait<WebDriver> seleniumWait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(500)); // driver, max czas, co ile

        seleniumWait.until(d1 -> {
            System.out.println("Uruchomienie: " + LocalDateTime.now());
            var element = d1.findElement(By.id("finish"));
            var textElement = element.findElement(By.tagName("h4"));
            var text = textElement.getText();

            if (text != null && !text.isEmpty()) {
                System.out.println("znalazłem");
                return true;
            }
            return false;
        });

        System.out.println("Wyświetla się");
    }

    @Test
    public void t4() {
        driver.get(URL);

        var buttonDiv = driver.findElement(By.id("start"));
        var button = buttonDiv.findElement(By.tagName("button"));
        button.click();

        Wait<WebDriver> seleniumWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(1))
                .pollingEvery(Duration.ofMillis(100))
                .withMessage("Moj message " + LocalDateTime.now());


        seleniumWait.until(d1 -> {
            System.out.println("Uruchomienie: " + LocalDateTime.now());
            var element = d1.findElement(By.id("finish"));
            var textElement = element.findElement(By.tagName("h4"));
            var text = textElement.getText();

            if (text != null && !text.isEmpty()) {
                System.out.println("znalazłem");
                return true;
            }
            return false;
        });

        System.out.println("Wyświetla się");
    }
}
