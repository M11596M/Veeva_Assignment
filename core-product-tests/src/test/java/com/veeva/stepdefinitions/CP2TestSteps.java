package com.veeva.stepdefinitions;

import com.veeva.pageObjects.HomePage;
import com.veeva.pageObjects.NewsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.nio.file.Paths;
import static com.veeva.utils.WebDriverManagerUtil.LOGGER;
import static com.veeva.utils.WebDriverManagerUtil.getDriver;

public class CP2TestSteps {
    HomePage homePage = new HomePage(getDriver());
    NewsPage newsPage = new NewsPage(getDriver());


    @Given("I am on the CP home page")
    public void i_am_on_the_cp_home_page() {
        LOGGER.info("Navigating to the NBA Warriors homepage...");
        homePage.openUrl("https://www.nba.com/warriors/",60);
        homePage.acceptCookies();
        homePage.closeFlex();
        homePage.hoverOnSecondaryMenu();
    }
    @When("I navigate to the New & Features section")
    public void i_navigate_to_the_new_features_section() {
        homePage.selectNewsAndFeaturesCategory();
        newsPage.verifyNewsPageLoaded();
    }
    @Then("I count the total number of video feeds")
    public void i_count_the_total_number_of_video_feeds() {
        newsPage.totalVideosFeed();
    }
    @Then("I count the number of video feeds older than {int} days")
    public void i_count_the_number_of_video_feeds_older_than_days(Integer days) {
        System.out.println("Total number of videos more than 3"+days+" is : "+newsPage.videoCounter());
    }

}

