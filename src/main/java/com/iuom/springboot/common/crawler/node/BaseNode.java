package com.iuom.springboot.common.crawler.node;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 *
 * 기본 노드
 *
 */
public abstract class BaseNode<T> {

    final int sleep_second = 2000;
    private WebDriver driver;
    private String driverType = "CHROME";

    public void initChrome(String url){
        System.setProperty("webdriver.chrome.driver", "c:\\workspace\\sw\\selenium\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.get(url);;
        this.driverType = "CHROME";
        sleep();
    }

    public void elementClick(WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).click().perform();
        sleep();
    }

    /**
     *
     * 시간 지연 처리한다.
     *
     */
    public void sleep() {
        try {
            Thread.sleep(sleep_second);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        switch (this.driverType) {
            case "CHROME" :
                getDriver().quit();
                break;
            default :
                break;
        }
    }

    public WebDriver getDriver() {
        switch (this.driverType) {
            case "CHROME" :
                if(driver == null) return new ChromeDriver();
                return driver;
            default :
                return null;
        }
    }

    public List<WebElement> findElements(WebElement webElement, By by) {
        try {
            return webElement.findElements(by);
        } catch (Exception e) {
            return null;
        }

    }

    public WebElement findElement(WebElement webElement, By by) throws Exception {
        try {
            return webElement.findElement(by);
        } catch (Exception e) {
            return null;
        }
    }

    public abstract T executeLogic();
}
