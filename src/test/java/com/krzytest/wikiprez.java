package com.krzytest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class wikiprez {

    private static final String URL = "https://www.wikipedia.org/";

    private WebDriver driver;

    @BeforeEach
    public void be() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=pl-PL");
        options.addArguments("--start-maximized");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver(options);
    }


    @Test
    public void testWikipedia() {
        driver.get(URL);

        var title = driver.getTitle();
       // WebDriver driver = new ChromeDriver();
       // driver.get("https://www.wikipedia.org/");

        var element = driver.findElement(By.className("central-textlogo"));
        var elementText = element.getText();
        System.out.print(elementText);
        // Przy użyciu ID
        //WebElement element1 = driver.findElement(By.id("mp-lower"));
       // WebElement textElement = element1.findElement(By.tagName("h4"));

        // Przy użyciu nazwy atrybutu
        //WebElement elementByAttribute = driver.findElement(By.cssSelector("[attribute='value']"));

        driver.quit();
    }

//    @AfterEach
//    public void ae() {
//        driver.quit();
//    }
}
