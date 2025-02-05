package com.veeva.stepdefenitions;


import com.veeva.pageObjects.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.veeva.utils.WebDriverManagerUtil.getDriver;

public class DP1TestSteps {
    HomePage homePage = new HomePage(getDriver());

    @Given("I am on the DP1 homepage")
    public void i_am_on_the_dp1_homepage() {
        homePage.openUrl("https://www.nba.com/sixers/  ");
       System.out.println("I am on the DP1 homepage");
    }

    @When("I navigate to the {string} menu")
    public void i_navigate_to_the_menu(String ticketsMenu) {
        System.out.println("I navigate to the : "+ ticketsMenu);
    }

    @Then("I count the number of slides present")
    public void i_count_the_number_of_slides_present() {
        System.out.println("I count the number of slides present");
    }

    @Then("I validate the title and duration of each slide")
    public void i_validate_the_title_and_duration_of_each_slide() {
        System.out.println("I validate the title and duration of each slide");
    }
}

