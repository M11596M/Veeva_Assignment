@CoreProductTest
Feature: Core Product Test for Jacket Details

  @CoreProductTest
  Scenario: Find Jackets from Men's section and store details
    Given I am on the homepage of NBA Warriors
    When I navigate to Men's category
    Then I extract jacket details and store them
