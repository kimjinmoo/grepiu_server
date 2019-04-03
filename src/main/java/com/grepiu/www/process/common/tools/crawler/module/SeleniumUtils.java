package com.grepiu.www.process.common.tools.crawler.module;

import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * Selenium 크롤링 유틸
 *
 */
public class SeleniumUtils {

  /**
   *
   * Root 엘리먼트에서 Loop 엘리먼트를 가져온다.
   *
   * @param webElement webElement 객체
   * @param by By 객체
   * @return List<WebElement> 객체
   */
  public static List<WebElement> findElements(WebElement webElement, By by) {
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
   * @param webElement WebElement 객체
   * @param by By 객체
   * @return WebElement 객체
   * @throws Exception
   */
  public static WebElement findElement(WebElement webElement, By by) throws Exception {
    try {
      return webElement.findElement(by);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   *
   * 클릭이벤트
   *
   * @param webDriver WebDriver 객체
   * @param webElement WebElement 객체
   */
  public static void elementClick(WebDriver webDriver, WebElement webElement) {
    Actions actions = new Actions(webDriver);
    actions.moveToElement(webElement).click().perform();
    sleep();
    ifShowAlertAccept(webDriver);
  }

  /**
   *
   * Text box에 입력
   *
   * @param webDriver WebDriver 객체
   * @param by By 객체
   * @param text String 객체
   * @throws Exception
   */
  public static void enterText(WebDriver webDriver, By by, String text) throws Exception {
    webDriver.findElement(By.name("q")).sendKeys(text);
  }

  /**
   *
   * 화면에 alert 여부 확인
   *
   * @param webDriver WebDriver
   * @return boolean
   * @throws Exception
   */
  public static void ifShowAlertAccept(WebDriver webDriver) {
    try {
      Alert alert = webDriver.switchTo().alert();
      alert.accept();
    } catch (NoAlertPresentException ex) {
      ex.getMessage();
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   *
   * alert를 허용 하지 않는다.
   *
   * @param webDriver WebDriver 객체
   */
  public static void isAlertDisable(WebDriver webDriver) {
    ((JavascriptExecutor) webDriver).executeScript("window.alert = function() {};");
  }

  /**
   * 시간 지연 처리한다.
   * 네트워크 사항등을 고려하여 변경해야될 필요도 있다.
   */
  public static void sleep() {
    try {
      Thread.sleep(CrawlerConstants.DEFAULT_SLEEP_SECOND);
    } catch (Exception e) {
      e.getMessage();
    }
  }
}
