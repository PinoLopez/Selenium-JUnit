# Selenium-JUnit

Automated Wikipedia tests using JUnit 5, Selenium 4, and Firefox headless on Linux.

## Stack
- Java 17
- JUnit Jupiter 5.10.2
- Selenium 4.18.1 (Selenium Manager auto-downloads geckodriver — no manual install needed)
- ExtentReports 5.1.1 (HTML report at `test-output/SparkReport/Index.html`)
- Maven 3.9+

## Run all tests
```bash
mvn clean test
```

## Report
After the run, open `test-output/SparkReport/Index.html` in Firefox.