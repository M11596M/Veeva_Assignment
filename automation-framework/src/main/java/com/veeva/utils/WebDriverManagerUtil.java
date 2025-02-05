package com.veeva.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WebDriverManager - Manages the initialization and lifecycle of WebDriver instances.
 * This class implements the Singleton pattern using ThreadLocal to ensure thread safety.
 */
public class WebDriverManagerUtil {

    public static final Logger LOGGER = Logger.getLogger(WebDriverManager.class.getName());

    // ThreadLocal variable to store the WebDriver instance per thread, ensuring parallel execution safety
    public static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> null);

    // Flag to ensure PageFactory is initialized only once
    private static boolean isPageFactoryInitialized = false;

    /**
     * A Map containing browser initialization strategies.
     * Uses Java 8 Functional Interfaces (Supplier) for lazy initialization.
     */
    private static final Map<String, Supplier<WebDriver>> BROWSER_MAP = new HashMap<>();

    static {
        BROWSER_MAP.put("chrome", () -> {
            LOGGER.info("Setting up Chrome WebDriver...");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (System.getProperty("headless", "false").equals("true")) {
                options.addArguments("--headless");
            }
            return new ChromeDriver(options);
        });

        BROWSER_MAP.put("firefox", () -> {
            LOGGER.info("Setting up Firefox WebDriver...");
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        });

        BROWSER_MAP.put("edge", () -> {
            LOGGER.info("Setting up Edge WebDriver...");
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        });

        BROWSER_MAP.put("safari", () -> {
            LOGGER.info("Setting up Safari WebDriver...");
            if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                LOGGER.severe("Safari is only supported on macOS.");
                throw new UnsupportedOperationException("Safari is only supported on macOS.");
            }
            return new SafariDriver();
        });
    }


    /**
     * Initializes the WebDriver based on the specified browser type.
     * If no browser is specified, Chrome is used as the default.
     */
    public static void initializeDriver(String browser) {
        if (driver.get() != null) {
            LOGGER.warning("WebDriver is already initialized.");
            return;
        }

        // Retrieve browser type from system properties, defaulting to Chrome if not provided
        String browserType = Optional.ofNullable(browser)
                .map(String::toLowerCase)
                .orElse(System.getProperty("browser", "chrome").toLowerCase().trim());
        LOGGER.info("Initializing WebDriver for browser: " + browserType);

        // Validate and initialize the WebDriver
        try {
            driver.set(Optional.ofNullable(BROWSER_MAP.get(browserType))
                    .orElseThrow(() -> {
                        LOGGER.severe("Unsupported browser: " + browserType);
                        return new IllegalArgumentException("Unsupported browser: " + browserType);
                    }).get());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during WebDriver initialization", e);
            throw e; // Rethrow to stop further execution
        }

        // Configure WebDriver settings after initialization
        configureDriver();

        // Initialize PageFactory only once
        if (!isPageFactoryInitialized) {
            initializePageFactory();
            isPageFactoryInitialized = true;
        }
    }

    /**
     * Configures common WebDriver settings:
     * - Maximizes the browser window.
     * - Sets implicit wait time to 10 seconds.
     * - Deletes all cookies to ensure a clean browser state.
     */
    private static void configureDriver() {
        LOGGER.info("Configuring WebDriver settings...");
        WebDriver webDriver = getDriver();
        webDriver.manage().window().maximize(); // Maximize browser window
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Set implicit wait
        webDriver.manage().deleteAllCookies(); // Clear cookies before test execution
    }

    /**
     * Initializes the PageFactory for the WebDriver.
     * This method will be called only once.
     */
    private static void initializePageFactory() {
        LOGGER.info("Initializing PageFactory...");
        PageFactory.initElements(getDriver(), WebDriverManagerUtil.class); // Initialize PageFactory for the WebDriver
    }

    /**
     * Retrieves the current WebDriver instance.
     * Throws an exception if the WebDriver is not initialized.
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return Optional.ofNullable(driver.get())
                .orElseThrow(() -> {
                    LOGGER.severe("WebDriver is not initialized! Call initializeDriver() first.");
                    return new IllegalStateException("WebDriver is not initialized! Call initializeDriver() first.");
                });
    }

    /**
     * Quits the WebDriver instance, closes the browser, and removes the reference.
     * If the driver is already closed, it simply removes the reference.
     */
    public static void quitDriver() {
        Optional.ofNullable(driver.get()).ifPresent(d -> {
            LOGGER.info("Closing WebDriver...");
            try {
                d.quit(); // Close the browser session
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error while quitting WebDriver", e);
            }
            driver.remove(); // Remove WebDriver reference from ThreadLocal
        });
    }
}
