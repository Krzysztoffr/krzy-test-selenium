package com.krzytest;

import com.krzytest.model.WikipediaHomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

public class WikipediaTest3 {

    // //div[@class="other-project"]//span[@class="other-project-tagline jsl10n"]

    //*[@id="www-wikipedia-org"]/div[8]/div[3]/div[1]/a/div[2]/span[2]


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
    public void xpath1() {
        driver.get(URL);

        var elements = driver.findElements(By.xpath("//div[@class=\"other-project\"]//span[@class=\"other-project-tagline jsl10n\"]"));
//        var elements = driver.findElements(By.xpath("""
                //div[@class="other-project"]//span[@class="other-project-tagline jsl10n"]"""));

        System.out.println(elements.size());

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
    }

    @Test
    public void pageObjectModelTestOne() {
        driver.get(URL);
        WikipediaHomePage wikipediaHomePage = PageFactory.initElements(driver, WikipediaHomePage.class);

        System.out.println(wikipediaHomePage.getEnglishBox().getText());
        System.out.println(wikipediaHomePage.getTitle().getText());

        var elements = wikipediaHomePage.getOtherProjects();

        System.out.println(elements.size());

        System.out.println(wikipediaHomePage.getOtherProjectsTexts());


    }

    @AfterEach
    public void ae() {
        driver.quit();
    }

}
