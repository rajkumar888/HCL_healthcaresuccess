package com.pages;

import com.utils.GenericUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Branding {

    WebDriver driver;
    WebDriverWait webDriverWait;
    GenericUtil genericUtil;

    public String checkOutTotalPrice;
    public String cartTotalPrice;
    public static Logger log = Logger.getLogger(Branding.class);


    public Branding(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 30);
        genericUtil = new GenericUtil(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//li[@data-cy='account']")
    WebElement btnSigIn;




    public boolean selectSubMenuUnderMore(String subMenu) {
        boolean flag = false;
        try {
            genericUtil.jsScrollUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }





}