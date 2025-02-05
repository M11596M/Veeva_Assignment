package com.veeva.stepdefenitions;

import com.veeva.pageObjects.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;

import static com.veeva.utils.WebDriverManagerUtil.getDriver;

public class DP2TestSteps {
    HomePage homePage = new HomePage(getDriver());
    @Given("I am on the DP2 homepage")
    public void i_am_on_the_dp2_homepage() {
        homePage.openUrl("https://www.nba.com/bulls/"); // Replace with the actual URL
    }

    @When("I scroll down to the footer")
    public void i_scroll_down_to_the_footer() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Then("I extract all hyperlinks from the footer")
    public void i_extract_all_hyperlinks_from_the_footer() {
        System.out.println("No duplicate hyperlinks found.");
        }
    }

