package com.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class HCL_UCDemo {

    public String browser = "chrome";
    public WebDriver driver;
    public static Logger log = Logger.getLogger(HCL_UCDemo.class);

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "True");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            log.info("Chrome Browser launched !!!");

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
            driver = new FirefoxDriver();
            log.info("Firefox Browser launched !!!");
        }

        log.info("Open healthcaresuccess.com website ");
        driver.manage().window().maximize();
        driver.get("https://healthcaresuccess.com/");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void UC_5() throws InterruptedException {
        WebElement element;
        log.info("User has navigated to Brandign Page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        element = driver.findElement(By.xpath("//img[@alt='Make My Trip']"));
        js.executeScript("arguments[0].click();", element);

    }

    @AfterMethod
    public void tearDown() {
        log.info("Method to close the browser ");
        driver.quit();
    }

}
