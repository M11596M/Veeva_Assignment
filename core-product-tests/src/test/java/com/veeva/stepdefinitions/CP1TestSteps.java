package com.veeva.stepdefinitions;

import com.veeva.pageObjects.HomePage;
import com.veeva.pageObjects.ProductPage;
import com.veeva.pageObjects.ShopPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.nio.file.Paths;
import static com.veeva.utils.WebDriverManagerUtil.LOGGER;
import static com.veeva.utils.WebDriverManagerUtil.getDriver;

public class CP1TestSteps {
    HomePage homePage = new HomePage(getDriver());
    ShopPage shopPage = new ShopPage(getDriver());
    ProductPage productPage = new ProductPage(getDriver());


    @Given("I am on the homepage of NBA Warriors")
    public void i_am_on_the_homepage_of_nba_warriors() {
        LOGGER.info("Navigating to the NBA Warriors homepage...");
        // Add WebDriver code to open the NBA Warriors homepage
        homePage.openUrl("https://www.nba.com/warriors/",60);
        homePage.acceptCookies();
        homePage.closeFlex();
        homePage.hoverOnShopMenu();

    }

    // Step for navigating to the Men's category
    @When("I navigate to Men's category")
    public void navigateToMensCategory() {
        shopPage.goToMensCategory();
        shopPage.verifyMensPageLoaded();
        shopPage.hoverOnMenTab();
        shopPage.selectJackets();
        shopPage.verifyMensJacketsPageLoaded();
    }

    // Step for getting jacket details
    @Then("I extract jacket details and store them")
    public void extractJacketDetails() {
        productPage.isTopSellersSelectedInDropdown();
        String filePath = Paths.get("jackets_details.txt").toAbsolutePath().toString(); // File path for storing jacket details
        productPage.extractProductDetails();
    }
}
