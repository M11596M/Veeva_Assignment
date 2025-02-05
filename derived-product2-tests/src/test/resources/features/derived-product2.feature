@FooterLinksExtraction
Feature: Extract and Validate Footer Links on DP2 Homepage

  Scenario: Extract footer links and identify duplicates
    Given I am on the DP2 homepage
    When I scroll down to the footer
    Then I extract all hyperlinks from the footer
    And I save the hyperlinks into a CSV file named "footer_links.csv"
    And I report any duplicate hyperlinks found
