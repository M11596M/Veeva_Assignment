package com.veeva.pageObjects;

import com.veeva.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HomePage extends BaseTest {


    // Locators
    private By menuItems = By.cssSelector(".menu-item");

    @FindAll(
            @FindBy(xpath = "//button[text()='I Accept']"))
    private WebElement acceptCookies;

    @FindAll(
            @FindBy(xpath ="//div[@class='w-full flex flex-end']/div"))
    private WebElement closeFlex;

    @FindBy(xpath = "//span[text()='Shop']")
    private WebElement shopMenu;

    @FindBy(xpath="//li[@class='menu-item']//span[text()='...']")
    private WebElement secondaryMenu;

    @FindBy(xpath ="//a[@title=\"Men's\"]")
    private WebElement mensCategory;

    @FindBy(xpath="//a[@title='News & Features']")
    private WebElement newsAndFeaturesCategory;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Method to accept cookies
    public void acceptCookies(){
        clickIfPresent(acceptCookies,20);
    }

    public void closeFlex(){
        clickIfPresent(closeFlex,15);
    }

    public void hoverOnShopMenu(){
        waitForElementToBeVisible(shopMenu,15);
        actions.moveToElement(shopMenu).perform();
    }

    public void hoverOnSecondaryMenu(){
        waitForElementToBeVisible(secondaryMenu,30);
        actions.moveToElement(secondaryMenu).perform();
    }

    public void selectMensCategory(){
        clickOnElement(mensCategory,15);
    }
    public void selectNewsAndFeaturesCategory(){
        waitForElementToBeVisible(newsAndFeaturesCategory,30);
        actions.moveToElement(newsAndFeaturesCategory,2,2).click().perform();
    }


    // Method to get all menu items using Java 8 Streams
    public List<String> getMenuItems() {
        List<WebElement> items = driver.findElements(menuItems);
        return items.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Using Optional to handle null or empty elements in a safer manner
    public Optional<String> getSpecificMenuItem(int index) {
        List<WebElement> items = driver.findElements(menuItems);
        return (index >= 0 && index < items.size())
                ? Optional.of(items.get(index).getText())
                : Optional.empty();
    }
}

