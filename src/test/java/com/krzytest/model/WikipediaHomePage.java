package com.krzytest.model;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;


@Data
public class WikipediaHomePage {
    @FindBy(className = "central-textlogo-wrapper")
    private WebElement title;

    @FindBy(id = "js-link-box-en")
    private WebElement englishBox;

    @FindBy(xpath = "//div[@class=\"other-project\"]//span[@class=\"other-project-tagline jsl10n\"]")
    private List<WebElement> otherProjects;

    public String getLanguage() {
        var split = englishBox.getText().split("\n");
        return split[0];
    }

    public List<String> getOtherProjectsTexts() {
        return otherProjects.stream()
                .map(WebElement::getText)
                .toList();
    }
}
