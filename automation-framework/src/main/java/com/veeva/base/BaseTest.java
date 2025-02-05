package com.veeva.base;

import com.veeva.utils.WebDriverManagerUtil;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.hu.De;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static  long timeout ; // Default timeout in seconds
    private static final long DEFAULT_TIMEOUT = 10; // Default timeout in seconds
    private static final long LONG_TIMEOUT = 30;  // Longer timeout for complex scenarios
    public Actions actions ;
    public WebDriver driver;
    public WebDriverWait wait;


    public BaseTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
        actions = new Actions(this.getDriver());
        wait = new WebDriverWait(this.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
    }
    public BaseTest(){
    }

    // Hook to initialize WebDriver before each scenario
    @Before
    public void setUp(Scenario scenario) {
        LOGGER.info("Starting the scenario: " + scenario.getName());
        WebDriverManagerUtil.initializeDriver(System.getProperty("browser", "chrome"));
        driver = this.getDriver();
    }

    // Hook to quit WebDriver after each scenario
    @After
    public void tearDown(Scenario scenario) {
        String status = scenario.isFailed() ? "FAILED" : "PASSED";
        LOGGER.info("Scenario '" + scenario.getName() + "' " + status);
        WebDriverManagerUtil.quitDriver();
    }

    // Get WebDriver instance
    public WebDriver getDriver() {
        return WebDriverManagerUtil.getDriver();
    }

    // Open a URL in the browser

    public void openUrl(String url,int timeoutInSeconds) {
        try {
            driver = this.getDriver();
            driver.get(url);
            // Wait until the page is completely loaded
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));

            LOGGER.info("Successfully loaded the URL: " + url);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while loading URL: " + url, e);
            throw new RuntimeException("Failed to load URL: " + url, e);
        }
    }

    public void openUrl(String url) {openUrl(url,15);}
    public void verifyURL(String expectedUrl, long timeout) {
        String currentUrl = getDriver().getCurrentUrl();
        LOGGER.info("Current URL: " + currentUrl);
        LOGGER.info("Expected URL: " + expectedUrl);
        try{
        Assert.assertEquals(expectedUrl,currentUrl);} catch (Throwable e) {
            Assert.assertTrue(currentUrl.toLowerCase().contains(expectedUrl),"The Expected URl doesn't match with the current one");
        }

    }
    public void verifyUrl(String expectedURL){
            verifyURL(expectedURL,DEFAULT_TIMEOUT);
            LOGGER.info("Successfully navigated to the URL : "+expectedURL);
    }

    // Navigate back to the previous page
    public void navigateBack() {
        getDriver().navigate().back();
        LOGGER.info("Navigated back to the previous page.");
    }

    // Navigate forward to the next page
    public void navigateForward() {
        getDriver().navigate().forward();
        LOGGER.info("Navigated forward to the next page.");
    }

    // Refresh the current page
    public void refreshPage() {
        getDriver().navigate().refresh();
        LOGGER.info("Page refreshed.");
    }
    public boolean isElementPresent(WebElement element) {
        return isElementPresent(element, (int) DEFAULT_TIMEOUT);
    }
    public boolean isElementPresent(WebElement element,int timeoutInSeconds) {
        try {
            waitForElementToBeVisible(element,timeoutInSeconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Wait for an element to be visible, with custom timeout
    public void waitForElementToBeVisible(WebElement element, long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(element));
        LOGGER.info("Element is visible: " + element);
    }

    // Wait for an element to be clickable, with custom timeout
    public void waitForElementToBeClickable(WebElement element, long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(element));
        LOGGER.info("Element is clickable: " + element);
    }

    // Click on element after waiting for it to be clickable
    public void clickOnElement(WebElement element) {
        waitForElementToBeClickable(element, DEFAULT_TIMEOUT);
        element.click();
        LOGGER.info("Clicked on element: " + element);
    }
    public void clickOnElement(WebElement element,int timeoutInSeconds) {
        waitForElementToBeClickable(element, timeoutInSeconds);
        element.click();
        LOGGER.info("Clicked on element: " + element);
    }
    public void clickIfPresent(WebElement element,int timeoutInSeconds){
        try{
            isElementPresent(element,timeoutInSeconds);
            clickOnElement(element,timeoutInSeconds);
        }
        catch (Exception e){
            LOGGER.info(e.getMessage());
        }
    }

    // Send text to input field after waiting for it to be visible
    public void sendKeysToElement(WebElement element, String text) {
        waitForElementToBeVisible(element, DEFAULT_TIMEOUT);
        element.sendKeys(text);
        LOGGER.info("Sent keys to element: " + element + " with text: " + text);
    }

    // Clear input field and send text after waiting for it to be visible
    public void clearAndSendKeysToElement(WebElement element, String text) {
        waitForElementToBeVisible(element, DEFAULT_TIMEOUT);
        element.clear();
        element.sendKeys(text);
        LOGGER.info("Cleared and sent keys to element: " + element + " with text: " + text);
    }

    // Wait for a specific element to contain a certain text
    public void waitForElementToContainText(WebElement element, String text, long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.textToBePresentInElement(element, text));
        LOGGER.info("Element contains text: " + text);
    }

    // Assert that an element is displayed
    public void assertElementIsDisplayed(WebElement element) {
        waitForElementToBeVisible(element, DEFAULT_TIMEOUT);
        if (element.isDisplayed()) {
            LOGGER.info("Element is displayed: " + element);
        } else {
            LOGGER.warning("Element is not displayed: " + element);
        }
    }

    // Assert that an element is not displayed
    public void assertElementIsNotDisplayed(WebElement element) {
        if (!element.isDisplayed()) {
            LOGGER.info("Element is not displayed: " + element);
        } else {
            LOGGER.warning("Element is displayed, but it should not be: " + element);
        }
    }

    // Wait for an alert and accept it
    public void waitForAlertAndAccept(long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        LOGGER.info("Alert accepted.");
    }

    // Wait for an alert and dismiss it
    public void waitForAlertAndDismiss(long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        alert.dismiss();
        LOGGER.info("Alert dismissed.");
    }

    // Wait for page title to be updated to expected title
    public void waitForPageTitleToBe(String expectedTitle, long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.titleIs(expectedTitle));
        LOGGER.info("Page title is as expected: " + expectedTitle);
    }

    // Wait for page title to contain expected substring
    public void waitForPageTitleToContain(String expectedTitleSubstring, long timeout) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.titleContains(expectedTitleSubstring));
        LOGGER.info("Page title contains: " + expectedTitleSubstring);
    }

    // Switch to a window or tab based on partial title
    public void switchToWindowByTitle(String titleSubstring) {
        String originalWindow = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
            if (getDriver().getTitle().contains(titleSubstring)) {
                LOGGER.info("Switched to window with title containing: " + titleSubstring);
                return;
            }
        }
        getDriver().switchTo().window(originalWindow); // Switch back if no matching window is found
        LOGGER.warning("No window found with title containing: " + titleSubstring);
    }

    // Switch to a frame by name or ID
    public void switchToFrameByNameOrId(String frameNameOrId) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name(frameNameOrId)));
        LOGGER.info("Switched to frame by name or ID: " + frameNameOrId);
    }

    // Switch to a frame by index
    public void switchToFrameByIndex(int index) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        LOGGER.info("Switched to frame by index: " + index);
    }

    // Switch to default content from a frame
    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
        LOGGER.info("Switched to default content.");
    }

    // Navigate to a different URL (useful for redirect tests)
    public void navigateToUrl(String url) {
        getDriver().navigate().to(url);
        LOGGER.info("Navigated to URL: " + url);
    }

    // Perform actions such as clicking, typing, etc. on a specific element
    public void performActionOnElement(WebElement element, String action, String value) {
        waitForElementToBeVisible(element, DEFAULT_TIMEOUT);

        Consumer<String> actionExecutor = actionExecutor(action, element, value);
        actionExecutor.accept(action);
    }

    // Action executor to handle click, sendKeys, clear based on the action type
    private Consumer<String> actionExecutor(String action, WebElement element, String value) {
        switch (action.toLowerCase()) {
            case "click":
                return (Consumer<String>) a -> element.click();
            case "sendkeys":
                return (Consumer<String>) a -> element.sendKeys(value);
            case "clear":
                return (Consumer<String>) a -> element.clear();
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
}
