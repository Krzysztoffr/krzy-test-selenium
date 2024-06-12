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

public class wikipediatestWithUsageofpageobjectmodel {

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
    public void xpathTestCheckSize_and_PrintElements() {
        driver.get(URL);

        var elements = driver.findElements(By.xpath("//div[@class=\"other-project\"]//span[@class=\"other-project-tagline jsl10n\"]"));
//        var elements = driver.findElements(By.xpath("""
                //div[@class="other-project"]//span[@class="other-project-tagline jsl10n"]"""));

        System.out.println(elements.size());

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
    }

 /**
 * This test method demonstrates the usage of Page Object Model (POM) design pattern.
 * It navigates to the Wikipedia homepage and initializes the WikipediaHomePage object using PageFactory.
 * Then, it prints the text of the English language box and the title of the page.
 * It also retrieves a list of 'other projects' elements and prints their size and texts.
 */
@Test
public void testPageObjectModelOne() {
    // Navigate to the Wikipedia homepage
    driver.get(URL);

    // Initialize the WikipediaHomePage object using PageFactory
    WikipediaHomePage wikipediaHomePage = PageFactory.initElements(driver, WikipediaHomePage.class);

    // Print the text of the English language box
    System.out.println(wikipediaHomePage.getEnglishBox().getText());

    // Print the title of the page
    System.out.println(wikipediaHomePage.getTitle().getText());

    // Retrieve a list of 'other projects' elements
    var elements = wikipediaHomePage.getOtherProjects();

    // Print the size of the 'other projects' elements list
    System.out.println(elements.size());

    // Print the texts of the 'other projects' elements
    System.out.println(wikipediaHomePage.getOtherProjectsTexts());
}

    @AfterEach
    public void ae() {
        driver.quit();
    }

}
