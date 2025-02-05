@CoreProductTest
Feature: Validate Slide Titles and Durations in the Tickets Menu on DP1 Homepage

  Scenario: Verify slide titles and durations
    Given I am on the DP1 homepage
    When I navigate to the "Tickets" menu
    Then I count the number of slides present
    And I validate the title and duration of each slide
