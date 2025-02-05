package com.veeva.pageObjects;

import com.veeva.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class ProductPage extends BaseTest {

    // Constructor to initialize WebDriver and PageFactory
    public ProductPage(WebDriver driver) {super(driver);}

    // Locators for the Shop page
    @FindBy(xpath = "//span[text()='Top Sellers']")
    WebElement topSellers;

    @FindBy(xpath="//div[contains(@class, 'product-card row')]")
    private List<WebElement> products;

    @FindBy(xpath="//div[@class='price-card']")
    private List<WebElement> priceCard;

    @FindBy(xpath="//div[@class='product-card-title']")
    private List<WebElement> productCardTitle;

    @FindBy(xpath="//div[contains(@class, 'product-badge-flag')]/text()")
    private List<WebElement> topSellerMessage;

    @FindBy(css = ".product-card")
    private List<WebElement> productCardElements;

    @FindBy(css = ".next-page")
    private WebElement nextButton;


    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (WebElement productElement : productCardElements) {
            Product product = new Product();
            PageFactory.initElements(new DefaultElementLocatorFactory(productElement), product);
            products.add(product);
        }
        return products;
    }

    public boolean isNextButtonEnabled() {
        return nextButton.isDisplayed() && nextButton.isEnabled();
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void isTopSellersSelectedInDropdown(){
        assertElementIsDisplayed(topSellers);
    }

    public void extractProductDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product_details.txt"))) {
            boolean hasNextPage = true;
            int i=1;
            int pageNumber =1;
            while (hasNextPage) {
                wait.until(d -> !getProducts().isEmpty());

                List<Product> products = getProducts();
                writer.write("Total number of products in page "+pageNumber+" are :  " + getProducts().size() + "\n");
                System.out.println("Total number of products in page "+pageNumber+" are :  " + getProducts().size() + "\n");

                for (Product product : products) {
                    writer.write("Product No: " + i + "\n");
                    System.out.println("Product No: " + i );
                    try{
                        // Write details to file
                        writer.write("Title: " + product.getTitle() + "\n");
                        System.out.println("Title: " + product.getTitle());
                        writer.write("Price: " + product.getPrice() + "\n");
                        System.out.println("Price: " + product.getPrice());
                        writer.write("Badge: " + product.getBadge() + "\n");
                        System.out.println("Badge: " + product.getBadge());

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    writer.write("---------------\n");
                    System.out.println("---------------");
                    i++;
                }

                if (isNextButtonEnabled()) {
                    clickNextButton();
                    wait.until(d -> !getProducts().isEmpty());
                    pageNumber++;
                } else {
                    hasNextPage = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

