package com.pages;

import com.utils.GenericUtil;
import excelUtility.ExcelReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Branding {

    WebDriver driver;
    WebDriverWait webDriverWait;
    GenericUtil genericUtil;
    static ExcelReader reader;

    public static Logger log = Logger.getLogger(Branding.class);

    public Branding(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 30);
        genericUtil = new GenericUtil(driver);
        PageFactory.initElements(driver, this);
        PropertyConfigurator.configure("./log4j.properties");
        try {
            reader = new ExcelReader(System.getProperty("user.dir") + "/src/main/resources/TestData.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FindBy(xpath = "//div[@class='wpb_wrapper']/h1")
    public WebElement lblMainH1Heading;

    @FindBy(xpath = "//div[@class='wpb_wrapper']/h2")
    public List<WebElement> lblMainH2Heading;

    @FindBy(xpath = "//div[@class='wpb_wrapper']/h2/following-sibling::p")
    public List<WebElement> lblParagraphSubText;


    public void navigateToBrandingPage() {
        driver.get("https://healthcaresuccess.com/marketing-services/branding");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        log.info("Navigated to Branding Page");
    }

    public String getBrandingPageTitle(){
        return driver.getTitle();
    }

    public boolean readAndStoreHeader1(){
        boolean flag=false;
        genericUtil.jsScrollToElement(lblMainH1Heading);
        String header1 = lblMainH1Heading.getText().trim();

        try {
            reader.setCellData("BrandingPage","MainHeader1", 2,header1);
            log.info(header1+" : is stored in excel sheet");
            flag=true;
        }catch (Exception e){
        System.out.println(e.getMessage());
        }
        return flag;
    }

    public boolean readAndStoreHeader2(){
        boolean flag=false;

        int rowNumber=2;

        for(WebElement ele : lblMainH2Heading){
            genericUtil.jsScrollToElement(ele);
            String header2 = ele.getText().trim();

            try {
                reader.setCellData("BrandingPage","MainHeader2", rowNumber++,header2);
                log.info(header2+" : is stored in excel sheet");
                flag=true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                flag=false;
            }
        }
        return flag;
    }


    public boolean readAndStoreSubtext(){
        boolean flag=false;

        int rowNumber=2;

        for(WebElement ele : lblParagraphSubText){
            genericUtil.jsScrollToElement(ele);
            String Subtext = ele.getText().trim();

            try {
                reader.setCellData("BrandingPage","Subtext", rowNumber++,Subtext);
                log.info(Subtext+" : is stored in excel sheet");
                flag=true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                flag=false;
            }
        }
        return flag;
    }
}
