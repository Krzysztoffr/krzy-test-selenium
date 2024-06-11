package com.krzytest;

import com.krzytest.model.WikipediaHomePage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class WikipediaFirstTest2 {

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
    public void openBrowser() {




        driver.get(URL);

        var title = driver.getTitle();

//        var element = driver.findElement(By.xpath("//*[@id=\"www-wikipedia-org\"]/div[1]/h1"));
        var element = driver.findElement(By.className("central-textlogo"));
        var elementText = element.getText();

        Assertions.assertThat(title)
                .isEqualTo("Wikipedia");

        Assertions.assertThat(elementText)
                .isEqualTo("Wikipedia\nWolna encyklopedia");
    }

    // praca domowa - kategorie z dołu (małe napisy)
    @Test
    public void checkLanguages() {
        var expected = List.of("Polski", "English", "Русский", "Español", "日本語", "Deutsch", "Français", "Italiano",
                "العربية", "Português");

        driver.get(URL);

        var elements = driver.findElements(By.className("central-featured-lang"));

        var languageList = elements.stream()
//                .map(webElement -> getLanguage(webElement))
                .map(this::getLanguage)
                .toList();

//        System.out.println(languageList);

//        Assertions.assertThat(languageList)
//                .containsExactlyElementsOf(expected);
//                .hasSameElementsAs(expected);


        var expected2 = new ArrayList<>(expected);

        for (String lan : languageList) {
            var first = expected2.get(0);
            if (!first.equals(lan)) {
                System.out.println("różnica kolejności");
                throw new RuntimeException("error-assert");
            }
            expected2.remove(0);
//
//            var bool = expected2.remove(lan);
//            if (!bool) {
//                System.out.println("nie bylo");
//                throw new RuntimeException("error-assert");
//            }
        }

        if (!expected2.isEmpty()) {
            System.out.println("nie-pusta");
            throw new RuntimeException("error-assert");
        }

        System.out.println("sukces");

    }

    @Test
    public void simpleRedirect() {
        var searchTxt = "Java";

        driver.get(URL);

        var element = driver.findElement(By.id("searchInput"));
        element.sendKeys(searchTxt + "123");
        element.clear();
        element.sendKeys(searchTxt);

        var button = driver.findElement(By.className("pure-button-primary-progressive"));
        button.click();

        Assertions.assertThat(driver.getTitle())
                .isEqualTo("Java – Wikipedia, wolna encyklopedia");

        var searchElementOnTopic = driver.findElement(By.name("search"));
        searchElementOnTopic.sendKeys("Pomarańcza");

        var searchButton = driver.findElement(By.className("cdx-search-input__end-button"));
        searchButton.click();

        Assertions.assertThat(driver.getTitle())
                .isEqualTo("Pomarańcza – Wikipedia, wolna encyklopedia");

        driver.navigate().back();

        Assertions.assertThat(driver.getTitle())
                .isEqualTo("Java – Wikipedia, wolna encyklopedia");
    }

    @AfterEach
    public void ae() {
//        driver.close();
        driver.quit();
    }

    @Test
    public void WikipediaLanguageTextTest() {
        driver.get(URL);

// Znajdź elementy zawierające napisy pod językami
        var elements = driver.findElements(By.className("central-featured-lang"));
        /*for (WebElement b : elements) {
            System.out.println(b.getText());
        }
        */

        var languageText = elements.stream()
//                .map(webElement -> getLanguageText(webElement))
                .map(this::getLanguageText)
                .toList();

        String result = String.join(", ", languageText);
        System.out.print(result);

        /*for (String a : languageText) {            System.out.print(a + ", ");        }*/
        System.out.print("\n");
        List<String> expected = List.of(
                "1 589 000+ haseł", "6,744,000+ articles", "1,906,000+ artículos", "1,947,000+ статей",
                "1,392,000+ 記事", "2,852,000+ Artikel", "2,567,000+ articles",
                "1,835,000+ voci", "1,387,000+ 条目 / 條目", "1,221,000+ مقالة"
        );
        String expectedResult = String.join(", ", expected);
        System.out.print(expectedResult);

        ;
        /*for (String a : expectedResult) {
            System.out.print(a);
        }*/
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void pageObjectModelTestOne() {
        driver.get(URL);


        WikipediaHomePage wikipediaHomePage = PageFactory.initElements(driver, WikipediaHomePage.class);

//        System.out.println(wikipediaHomePage.getTitle().getText());
        System.out.println(wikipediaHomePage.getLanguage());


    }

    @Test
    void selector1() {
        driver.get(URL);

        var elements = driver.findElements(By.cssSelector("div.central-featured-lang strong"));

        System.out.println(elements.size());

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
    }

    private String getLanguage(WebElement webElement) {
        return webElement.findElement(By.tagName("strong")).getText();
    }

    private String getLanguageText(WebElement webElement) {
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