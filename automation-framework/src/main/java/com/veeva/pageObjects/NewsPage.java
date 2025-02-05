package com.veeva.pageObjects;

import com.veeva.base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class NewsPage extends BaseTest {
    @FindBy(xpath="//div[@data-testid='post-duration']/../time")
    private List<WebElement> videos;

    @FindBy(css = ".next-page")
    private List<WebElement> nextButton;

    public NewsPage(WebDriver driver) {
        super(driver);
    }
    public void verifyNewsPageLoaded() {
        verifyUrl("https://www.nba.com/warriors/news");
    }

    public int totalVideosFeed(){return videos.size();}

    public int videoCounter(){
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Initialize counters
        int totalVideos = 0;
        int videosOlderThan3Days = 0;

        // Define the date format (adjust if necessary)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);

        boolean hasNextPage = true;

        while (hasNextPage) {
            for (WebElement timeElement : videos) {
                String datetime = timeElement.getAttribute("datetime");
                LocalDate videoDate = LocalDate.parse(datetime, formatter);

                // Calculate the difference in days
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(videoDate, currentDate);

                // Increment total videos count
                totalVideos++;

                if (daysBetween > 3) {
                    // Increment count for videos older than 3 days
                    videosOlderThan3Days++;
                }
            }

            // Check for the presence of a "Next" button to navigate to the next page
            if (!nextButton.isEmpty() && nextButton.get(0).isEnabled()) {
                nextButton.get(0).click();
            } else {
                hasNextPage = false;
            }
        }

        // Output the results
        System.out.println("Total videos: " + totalVideos);
        System.out.println("Videos older than 3 days: " + videosOlderThan3Days);
        return videosOlderThan3Days;

    }

}
