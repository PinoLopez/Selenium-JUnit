package com.wikipedia;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@ExtendWith(ExtentTestWatcher.class)
public abstract class BaseTest {

    // ── ExtentReports: initialised once for the entire test run ──────────────
    protected static final ExtentReports extent;
    protected static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ── WebDriver: one fresh instance per test method ────────────────────────
    protected WebDriver driver;
    protected WebDriverWait wait;

    static {
        new java.io.File("test-output/SparkReport").mkdirs();

        ExtentSparkReporter spark =
                new ExtentSparkReporter("test-output/SparkReport/Index.html");
        spark.config().setReportName("Selenium-JUnit Wikipedia Tests");
        spark.config().setDocumentTitle("Selenium Test Report");
        spark.config().setTheme(Theme.DARK);
        spark.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS",      System.getProperty("os.name"));
        extent.setSystemInfo("Java",    System.getProperty("java.version"));
        extent.setSystemInfo("Browser", "Firefox (headless)");

        // Flush the report to disk when the JVM exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> extent.flush()));
    }

    // ── Runs before every @Test: creates the ExtentTest node, starts driver ──
    @BeforeEach
    void baseSetUp(TestInfo testInfo) {
        ExtentTest test = extent.createTest(
                getClass().getSimpleName() + " → " + testInfo.getDisplayName());
        extentTest.set(test);
        initDriver();
    }

    // ── Runs after every @Test: quits the driver ─────────────────────────────
    @AfterEach
    void baseTearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // ── Starts Firefox headless via Selenium Manager (no manual geckodriver) ─
    protected void initDriver() {
        int maxAttempts = 3;
        Exception lastException = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                driver = new FirefoxDriver(buildOptions());
                wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
                return;
            } catch (Exception e) {
                lastException = e;
                System.out.println("[initDriver] Attempt " + attempt
                        + " failed: " + e.getMessage() + ". Retrying in 2 s…");
                try { Thread.sleep(2000); } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException(
                "FirefoxDriver could not start after " + maxAttempts + " attempts.",
                lastException);
    }

    private FirefoxOptions buildOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }
}