package com.test;

import com.pages.Branding;
import com.utils.Drivers;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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

        PropertyConfigurator.configure("log4j.properties");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new Drivers().launchDriver("chrome");
            log.info("Chrome Browser launched !!!");

        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new Drivers().launchDriver("firefox");
            log.info("Firefox Browser launched !!!");
        }

        log.info("Open website healthcaresuccess.com  ");
        driver.manage().window().maximize();
        driver.get("https://healthcaresuccess.com/");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void UC_5() throws InterruptedException {
        Branding branding = new Branding(driver);
        branding.navigateToBrandingPage();

        String expectedTitle="Healthcare Branding Services to Differentiate You From the Competition";
        String actualTitle=branding.getBrandingPageTitle();
        Assert.assertEquals(expectedTitle,actualTitle,"Title didn't match");
        Assert.assertTrue(branding.readAndStoreHeader1());
        Assert.assertTrue(branding.readAndStoreHeader2());
        Assert.assertTrue(branding.readAndStoreSubtext());
    }

    @AfterMethod
    public void tearDown() {
        log.info("Method to close the browser");
        driver.quit();
    }

}
