package com.iuom.springboot.common.crawler.node;

import com.iuom.springboot.common.crawler.domain.Cinema;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 크롤링 기본 노드 - selenium 기반 개발
 */
public abstract class BaseNode<T> {

  final int sleep_second = 2000;
  private WebDriver driver;
  private String driverType = "CHROME";

  private final String chromePath = "/usr/bin/chromedriver";
  private final String firefoxPath = "/home/sw/firefox";

  /**
   * 크롬으로 초기화
   */
  public void initChrome(String url) {

//    System.setProperty("webdriver.chrome.driver", chromePath);
        System.setProperty("webdriver.chrome.driver", "c:\\workspace\\sw\\selenium\\chromedriver.exe");
    this.driver = new ChromeDriver();
    this.driver.get(url);
    ;
    this.driverType = "CHROME";
    sleep();
  }

  public void initFirefox(String url) {
    System.setProperty("webdriver.gecko.driver", firefoxPath);
//    System.setProperty("webdriver.gecko.driver", "c:\\workspace\\sw\\selenium\\geckodriver.exe");
    this.driver = new FirefoxDriver();
    this.driver.get(url);
    ;
    this.driverType = "FIREFOX";
    sleep();
  }

  /**
   * 시간 지연 처리한다.
   * 네트워크 사항등을 고려하여 변경해야될 필요도 있다.
   * 기본값 : 2초
   */
  public void sleep() {
    try {
      Thread.sleep(sleep_second);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void quit() {
    getDriver().quit();
  }

  public WebDriver getDriver() {
    switch (this.driverType) {
      case "CHROME":
        return driver;
      case "FIREFOX":
        return driver;
      default:
        return null;
    }
  }

  /**
   *
   * Root 엘리먼트에서 Loop 엘리먼트를 가져온다.
   *
   * @param webElement
   * @param by
   * @return
   */
  public List<WebElement> findElements(WebElement webElement, By by) {
    try {
      return webElement.findElements(by);
    } catch (Exception e) {
      return null;
    }

  }

  /**
   *
   * Root엘리먼트에서 특정 엘리먼트를 가져온다.
   *
   * @param webElement
   * @param by
   * @return
   * @throws Exception
   */
  public WebElement findElement(WebElement webElement, By by) throws Exception {
    try {
      return webElement.findElement(by);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 엘리먼트를 클릭한다.
   */
  public void elementClick(WebElement webElement) {
    Actions actions = new Actions(driver);
    actions.moveToElement(webElement).click().perform();
    sleep();
  }


  /**
   * 실 구동 로직 구현
   * <pre>
   *     BaseNode를 상속받아 executeLogic 로직 구현
   *     특정 이벤트처리를 위해 onUpdate 지정하여 사용
   * </pre>
   */
  public abstract List<Cinema> executeLogic();
}
