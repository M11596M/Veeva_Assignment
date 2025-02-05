package com.veeva.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Product {
    @FindBy(css = ".product-card-title")
    private WebElement titleElement;

    @FindBy(css = ".price")
    private WebElement priceElement;

    @FindBy(css = ".product-badge-flag")
    private WebElement badgeElement;

    public String getTitle() {
        return titleElement.getText();
    }

    public String getPrice() {
        return priceElement.getText();
    }

    public String getBadge() {
        return badgeElement != null ? badgeElement.getText() : "";
    }
}
