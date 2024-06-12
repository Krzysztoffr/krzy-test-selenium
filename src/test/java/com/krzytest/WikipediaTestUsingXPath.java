package com.krzytest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class WikipediaTestUsingXPath {

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

    @AfterEach
    public void ae() {
        driver.quit();
    }

    @Test
    public void testElementsWikiSizeAndContent() {
        driver.get(URL);
        List<WebElement> elementyWiki = driver.findElements(By.xpath("//*[@id=\"www-wikipedia-org\"]/main/nav[1]/div[*]"));
        System.out.println(elementyWiki.size());

        for (WebElement element : elementyWiki) {
            System.out.println(element.getText());
        }
    }

    @Test

    public void testWikiListElementsEquality_With_JUnit_sendKeys() throws InterruptedException {
        driver.get(URL);

        // Wynik oczekiwany (to, co oczekujesz, że znajdziesz na stronie)
        List<String> expectedList = Arrays.asList("przypadki testowe", "oddzielenie testów od kodu",
                "wiele mechanizmów uruchamiania", "tworzenie raportów", "integracja z różnymi środowiskami programistycznymi.");

        String expectedResult = String.join(", ", expectedList);
        System.out.print(expectedResult);

//wpisuje JUnit w wyszukiwarkę
        var searchElementButton = driver.findElement(By.name("search"));
        searchElementButton.sendKeys("JUnit");
        //Thread.sleep(Duration.ofSeconds(1));
//+ klika
        var searchButton = driver.findElement(By.cssSelector("#search-form > fieldset > button > i"));
        searchButton.click();
        //Thread.sleep(Duration.ofSeconds(1));
//wyszukuje elementy
        var JUnitElementyWiki = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/ul[1]"));
        //Thread.sleep(Duration.ofSeconds(1));

        //System.out.println(JUnitElementyWiki.size());

// Mapowanie do listy
        List<String> JUnitElementyWikiList = JUnitElementyWiki.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
//drukuje
        //String result = String.join(", ", JUnitElementyWikiList);
        System.out.print(JUnitElementyWikiList);

        boolean listsAreEqual = true;
        if (expectedList.size() == JUnitElementyWikiList.size()) {
            for (int i = 0; i < expectedList.size(); i++) {
                if (!expectedList.get(i).equals(JUnitElementyWikiList.get(i))) {
                    break;
                }
            }
        } else {
            listsAreEqual = false;
        }
    }


    }







