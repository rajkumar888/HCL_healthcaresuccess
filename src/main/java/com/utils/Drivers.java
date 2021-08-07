package com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Drivers {
    WebDriver driver;

    public WebDriver launchDriver(String desiredBrowser) {
        switch (desiredBrowser.toLowerCase()) {
            case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "True");
                WebDriverManager.chromedriver().setup();
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new NotImplementedException("Desired Browser is not Found");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }
}
