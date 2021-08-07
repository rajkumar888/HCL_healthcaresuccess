package com.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.pages.Branding;
import com.utils.Drivers;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HCL_UCDemo {

    public WebDriver driver;
    public Logger log = Logger.getLogger(HCL_UCDemo.class);
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @Parameters("browser")
    @BeforeTest
    public void setup(String browser) {

        PropertyConfigurator.configure("log4j.properties");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/log/ExtentReport " + new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss").format(new Date()) + ".html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //To add system or environment info by using the setSystemInfo method.
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Browser", browser);

        //configuration items to change the look and feel
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("HCL Extent Report");
        htmlReporter.config().setReportName("HCL Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new Drivers().launchDriver("chrome");
            log.info("Chrome Browser launched !!!");

        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new Drivers().launchDriver("firefox");
            log.info("Firefox Browser launched !!!");
        }

        log.info("Opening website healthcaresuccess.com  ");
        driver.manage().window().maximize();
        driver.get("https://healthcaresuccess.com/");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void UC_5() throws InterruptedException {

        test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName()+" Started....");

        Branding branding = new Branding(driver);
        branding.navigateToBrandingPage();

        String expectedTitle = "Healthcare Branding Services to Differentiate You From the Competition";
        String actualTitle = branding.getBrandingPageTitle();
        Assert.assertEquals(expectedTitle, actualTitle, "Title didn't match");
        Assert.assertTrue(branding.readAndStoreHeader1());
        Assert.assertTrue(branding.readAndStoreHeader2());
        Assert.assertTrue(branding.readAndStoreSubtext());
    }


    @Test()
    public void UC_6DummyFail(){
        test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName()+" Started....");
        Assert.fail();
    }

    @Test()
    public void UC_6DummyPass(){
        test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName()+" Started....");
        Assert.assertTrue(true);
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " .....FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " ..... PASSED ", ExtentColor.GREEN));
        } else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " .....SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }

    @AfterTest
    public void tearDown() {
        test.info("Method to close the browser");
        driver.quit();
        extent.flush();
    }
}
