package com.iuom.springboot.common.crawler.node;

import com.google.common.collect.ImmutableMap;
import com.iuom.springboot.common.crawler.domain.Cinema;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * 크롤링 기본 노드
 *    - selenium 기반 개발
 *
 */
public abstract class BaseNode<T> {

    final int sleep_second = 2000;
    private WebDriver driver;
    private String driverType = "CHROME";

    private final String chromePath =  "/usr/bin/chromedriver";
    public static ChromeDriverService service;

    /**
     *
     * 크롬으로 초기화
     *
     * @param url
     */
    public void initChrome(String url){
        try {
            if(service == null) {
                service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(chromePath))
                    .usingAnyFreePort()
                    .withEnvironment(ImmutableMap.of("DISPLAY",":0"))
                    .withSilent(true)
                    .build();
                service.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", chromePath);
//        System.setProperty("webdriver.chrome.driver", "c:\\workspace\\sw\\selenium\\chromedriver.exe");
        this.driver = new ChromeDriver(service);
        this.driver.get(url);;
        this.driverType = "CHROME";
        sleep();
    }

    /**
     *
     * 엘리먼트 클릭
     *
     * @param webElement
     */
    public void elementClick(WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).click().perform();
        sleep();
    }

    /**
     *
     * 이벤트 리스너 등록
     *
     * @param listener
     */
    public void onUpdate(Consumer<Object> listener) {
        try {
            listener.accept(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                if(service != null) service.stop();
                break;
            default :
                break;
        }
    }

    public WebDriver getDriver() {
        switch (this.driverType) {
            case "CHROME" :
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

    /**
     *
     * 실 구동 로직 구현
     * <pre>
     *     BaseNode를 상속받아 executeLogic 로직 구현
     *     특정 이벤트처리를 위해 onUpdate 지정하여 사용
     * </pre>
     *
     * @return
     */
    public abstract List<Cinema> executeLogic();
}
