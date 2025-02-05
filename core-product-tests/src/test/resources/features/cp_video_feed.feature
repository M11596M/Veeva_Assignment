@CoreProductTest
Feature: Count Video Feeds on CP Website

@CoreProductTest
  Scenario: Count total videos and videos older than 3 days
    Given I am on the CP home page
    When I navigate to the New & Features section
    Then I count the total number of video feeds
    And I count the number of video feeds older than 3 days