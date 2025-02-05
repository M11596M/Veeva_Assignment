package com.veeva.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.testng.annotations.DataProvider;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/", // Path to the feature file
        glue = {"com/veeva/stepdefinitions", "com/veeva/base"}, // Path to the step definition class
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true, // Make the output readable
        tags = "@CoreProductTest" // Tags to run specific tests
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}