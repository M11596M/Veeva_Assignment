package com.veeva.pageObjects;

import com.veeva.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.veeva.utils.WebDriverManagerUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShopPage extends BaseTest {

    // Constructor to initialize WebDriver and PageFactory
    public ShopPage(WebDriver driver) {
        super(driver);
    }

    // Locators for the Shop page
    @FindBy(xpath = "//a[contains(@href, 'mens')]")
    WebElement mensCategory;

    @FindBy(xpath = "//a[@aria-label='Men']")
    WebElement menTab;

    @FindBy(xpath = "//div[text()='Jackets']")
    WebElement jackets;

    @FindBy(xpath = "//div[contains(@class, 'pagination')]//a")
    List<WebElement> paginationLinks;

    // Method to navigate to the 'Men's' section
    public void goToMensCategory() {
        clickOnElement(mensCategory,30);
    }
    public void verifyMensPageLoaded() {
        verifyUrl("golden-state-warriors");
    }
    public void hoverOnMenTab(){
        waitForElementToBeVisible(menTab,15);
        actions.moveToElement(menTab).perform();
    }
    public void selectJackets(){
        clickOnElement(jackets);
    }
    public void verifyMensJacketsPageLoaded(){
        verifyUrl("https://shop.warriors.com/golden-state-warriors-men-jackets");
    }
}
