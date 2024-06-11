package com.krzytest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;

public class WaitsDynamicLoading2Test {
   /*
Praca domowa zrób 4 waity dla przykładu z
https://the-internet.herokuapp.com/dynamic_loading/2

isDisplayed()

https://hackernoon.com/recommended-websites-to-practice-selenium-and-test-automation
 */
   private final String URL = "https://the-internet.herokuapp.com/dynamic_loading/2";

    private WebDriver driver;

    @BeforeEach
    public void be() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=pl-Pl");
        options.addArguments("--start-maximized");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void ae() {
//        driver.close();
        driver.quit();
    }
//Thread.sleep() Method in Java
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

        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500))
                  //.withMessage("Start")
                .until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
//lub //.xpath("//*[@id='start']/button")));
        button.click();

        WebElement button2 = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(500))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        System.out.println(button2.getText());

        Assertions.assertThat(button2.getText()).isEqualTo("Hello World!");
        driver.quit();
    }

    @Test
    public void t3() {
        driver.get(URL);

        var buttonDiv = driver.findElement(By.id("start"));
        var button = buttonDiv.findElement(By.tagName("button"));
        button.click();

        Wait<WebDriver> seleniumWait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(10)); // driver, max czas, co ile

        seleniumWait.until(d1 -> {
            System.out.println("Uruchomienie: " + LocalDateTime.now());
            var element = d1.findElement(By.id("finish"));
            var textElement = element.findElement(By.tagName("h4"));
            var text = textElement.getText();
            System.out.println(textElement.getText());

            if (text != null && !text.isEmpty()) {
                System.out.println("znalazłem");
                return true;
            }
            return false;
        });
        System.out.println("Wyświetla się");
    }

    @Test
    public void t4() throws InterruptedException {
        driver.get(URL);

        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500))
                //.withMessage("Start")
                .until(ExpectedConditions.elementToBeClickable(By.tagName("button")));


//lub  //.xpath("//*[@id='start']/button")));
        button.click();
Thread.sleep(700);


//        WebElement NewButton = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500))
//                //.withMessage("Start")
//                .until(ExpectedConditions.elementToBeClickable(By.id("finish")));
//        System.out.println(NewButton.getText());

        WebElement NewButton = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .until(ExpectedConditions.elementToBeClickable(By.id("finish")));

        System.out.println(NewButton.getText());

Assertions.assertThat(NewButton.getText()).isEqualTo("Hello World!");
        driver.quit();
    }

}
