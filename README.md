Veeva Web Test Automation Framework

Overview

The Veeva Web Test Automation Framework is a modular, scalable, and reusable test automation framework designed to automate the testing of multiple products (Core Product, Derived Product 1, and Derived Product 2). The framework is implemented using Selenium WebDriver, Java, TestNG, Cucumber (BDD), Maven, and Extent Reports for reporting.

This project follows the Page Object Model (POM) with Page Factory and supports parallel execution, parameterized configuration settings, and limited use of static waits to enhance test stability and performance.

Project Structure

Veeva-Web-Test-Automation/
├── automation-framework/               # Core automation framework
│   ├── src/main/java/com/automation/
│   │   ├── pageobjects/                # Page Object Model (POM) classes
│   │   │   ├── HomePage.java
│   │   │   └── ProductPage.java
│   │   ├── utils/                       # Utility classes
│   │   │   ├── WebDriverManager.java
│   │   │   └── DataReader.java
│   │   ├── drivers/                     # WebDriver factory classes
│   │   │   └── BrowserFactory.java
│   │   ├── config/                      # Configuration files
│   │   │   └── ConfigProperties.java
│   │   └── listeners/                   # TestNG and Extent Report listeners
│   │       └── ExtentReportListener.java
│   ├── pom.xml
│   └── README.md
│
├── core-product-tests/                  # Test cases for Core Product
│   ├── src/test/java/com/automation/
│   │   ├── stepdefinitions/             # Step definition files for Cucumber
│   │   │   └── CPTestSteps.java
│   │   ├── runners/                     # Test runners
│   │   │   └── CPTestRunner.java
│   │   └── tests/
│   │       └── CPTestNGSuite.xml       # TestNG suite file
│   ├── src/test/resources/features/
│   │   └── core-product.feature
│   ├── src/test/resources/testdata/
│   │   └── core-product-testdata.json
│   ├── pom.xml
│
├── derived-product1-tests/               # Test cases for Derived Product 1
│   ├── src/test/java/com/automation/
│   │   ├── stepdefinitions/
│   │   │   └── DP1TestSteps.java
│   │   ├── runners/
│   │   │   └── DP1TestRunner.java
│   │   └── tests/
│   │       └── DP1TestNGSuite.xml
│   ├── src/test/resources/features/
│   │   └── derived-product1.feature
│   ├── src/test/resources/testdata/
│   │   └── derived-product1-testdata.json
│   ├── pom.xml
│
├── derived-product2-tests/               # Test cases for Derived Product 2
│   ├── src/test/java/com/automation/
│   │   ├── stepdefinitions/
│   │   │   └── DP2TestSteps.java
│   │   ├── runners/
│   │   │   └── DP2TestRunner.java
│   │   └── tests/
│   │       └── DP2TestNGSuite.xml
│   ├── src/test/resources/features/
│   │   └── derived-product2.feature
│   ├── src/test/resources/testdata/
│   │   └── derived-product2-testdata.json
│   ├── pom.xml
│
├── pom.xml                               # Parent POM for multi-module project
└── README.md                             # Project documentation

Setup & Installation

Prerequisites

Ensure the following dependencies are installed:

Java 11 or later

Maven 3.6+

Selenium WebDriver

TestNG

Cucumber

WebDriverManager

Extent Reports

JSON/YAML Processing Libraries

Steps to Setup

Clone the repository:

git clone https://github.com/your-repo/Veeva-Web-Test-Automation.git

Navigate to the project directory:

cd Veeva-Web-Test-Automation

Build the project using Maven:

mvn clean install

Running Tests

Using Maven

Run all tests across modules:

mvn test

Run specific module tests:

cd core-product-tests
mvn test

Using TestNG

Run a specific TestNG suite:

mvn test -DsuiteXmlFile=src/test/resources/tests/CPTestNGSuite.xml

Using Cucumber

Run Cucumber tests:

mvn test -Dcucumber.features=src/test/resources/features/core-product.feature

Reporting

Test execution reports are generated using Extent Reports. After test execution, reports can be found at:

{project-root}/test-output/ExtentReport.html

Open the HTML report in a browser to view the detailed execution results.

CI/CD Integration

This project supports Jenkins CI/CD pipelines. To integrate:

Configure Jenkins to pull the latest code from the repository.

Add a Maven build step with:

mvn clean test

Post-test execution, configure Jenkins to archive reports from:

test-output/ExtentReport.html

Best Practices

Follow POM (Page Object Model) for maintainability.

Use WebDriverManager for driver initialization.

Avoid hard-coded values; use JSON/YAML for test data.

Enable parallel execution for better performance.

Use proper logging and reporting mechanisms.

Contributors

Madhan Mohan Reddy (Automation Engineer)

Team Members (Add names if applicable)

For any queries, contact: m11596m@gmail.com

License

This project is licensed under the MIT License - see the LICENSE file for details.

