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

class WikipediaFirstTest {

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
                .isEqualTo("Wikipedia");
    }

    // praca domowa - kategorie z dołu (małe napisy)
    @Test
    public void checkLanguages() {
        driver.get(URL);

        var elements = driver.findElements(By.className("central-featured-lang"));
        var languageList = elements.stream().limit(8)
//                .map(webElement -> getLanguage(webElement))
                .map(this::getLanguage)
                .toList();
        String result = String.join(", ", languageList);
        System.out.print(result);

        var expected = List.of("Polski", "English", "Русский", "Español", "日本語", "Deutsch", "Français", "Italiano");
        var expected2 = new ArrayList<>(expected);

        //porównanie każdy element lan z listy languageList z odpowiadającym mu elementem first z listy expected2
        for (String lan : languageList) {
            var first = expected2.get(0);
            if (!first.equals(lan)) {
                System.out.println("\n różnica kolejności");
                throw new RuntimeException("\n error-assert");
            }
            expected2.remove(0);
           //var bool = expected2.remove(lan); if (!bool) { System.out.println("nie bylo");  throw new RuntimeException("error-assert");  }
        }

        if (!expected2.isEmpty()) {
            System.out.println("\n nie-pusta");
            throw new RuntimeException("\n error-assert");
        }
        System.out.println("\n sukces");

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
                "1 591 000+ haseł", "6 751 000+ articles", "1 949 000+ статей", "1 909 000+ artículos", "1 394 000+ 記事",
                "2 856 000+ Artikel", "2 571 000+ articles", "1 837 000+ voci", "1 389 000+ 条目 / 條目", "العربية مقالة"
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
    public void WikipediaOtherProjectTest() {
        driver.get(URL);

        // Znajdź wszystkie elementy o klasie 'other-project'
        var elements = driver.findElements(By.className("other-project"));

        // Przyporządkuj każdy element do jego tytułu i tagline za pomocą metody getOtherProjectInfo
        var otherProjectsInfo = elements.stream()
                .map(this::getOtherProjectInfo)
                .toList();

        // Połącz wyniki i wydrukuj
        String result = String.join(", ", otherProjectsInfo);
        System.out.print(result);

        System.out.print("\n");
        List<String> expected = List.of(
                "Commons: Zdjęcia do swobodnego wykorzystywania"
                , "Wikipodróże: Przewodnik Turystyczny"
                , "Wikisłownik: Wolny słownik", "Wikibooks: Otwarte podręczniki"
                , "Wikinews: Wolne źródło informacji"
                , "Wikidane: Wolna baza wiedzy"
                , "Wikiwersytet: Wolne materiały edukacyjne"
                , "Wikicytaty: Kolekcja cytatów"
                , "MediaWiki: Darmowa i otwarta aplikacja wiki"
                , "Wikiźródła: Wolne materiały źródłowe"
                , "Wikispecies: Wolny katalog gatunków"
                , "Wikifunctions: Free function library"
                , "Meta-Wiki: Koordynacja społeczności i dokumentacja"
        );
        String expectedResult = String.join(", ", expected);
        System.out.print(expectedResult);

        Assertions.assertThat(result).isEqualTo(expectedResult);

    }


    @Test
    public void pageObjectModelTestOne() {
        driver.get(URL);


        WikipediaHomePage wikipediaHomePage = PageFactory.initElements(driver, WikipediaHomePage.class);

//        System.out.println(wikipediaHomePage.getTitle().getText());
        System.out.println(wikipediaHomePage.getLanguage());


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

    private String getOtherProjectInfo(WebElement webElement) {
        try {
            // element <span> klasa 'other-project-title' (tytuł)
            WebElement titleElement = webElement.findElement(By.className("other-project-title"));
            // element <span> klasa 'other-project-tagline' (tagline)
            WebElement taglineElement = webElement.findElement(By.className("other-project-tagline"));


            String title = titleElement.getText();
            String tagline = taglineElement.getText();

            return title + ": " + tagline;
        } catch (NoSuchElementException e) {
            System.out.println("Nie znaleziono elementu: " + e.getMessage());
            return "Element not found";
        }
    }
}

