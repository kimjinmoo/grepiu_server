package com.grepiu.www.process.common.tools.crawler.module;

import com.grepiu.www.process.common.tools.crawler.node.SeleniumExecuteNode;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * 크롤링 기본 노드 - selenium 기반 개발
 * // 프록시 List : http://www.gatherproxy.com/proxylist/country/?c=Republic%20of%20Korea
 * // 프록시 메뉴얼 : https://www.seleniumhq.org/docs/04_webdriver_advanced.jsp
 *
 */
public class SeleniumConnect<T> implements Connect<T>{

  private WebDriver driver; // WebDriver
  private SeleniumExecuteNode<T> seleniumExecuteNode; // 실행 노드

  /**
   *
   * 기본 설정 값으로 초기화
   *
   * @param seleniumExecuteNode 크롤링 구현체
   */
  @Override
  public void init(SeleniumExecuteNode<T> seleniumExecuteNode) {
    try {
      //step1. seleniumExecuteNode Set
      this.seleniumExecuteNode = seleniumExecuteNode;

      DesiredCapabilities dc = new DesiredCapabilities();
      dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
      //step2. driver 초기화
      this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER),
          dc.chrome());
    } catch (Exception e) {
      throw new RuntimeException("초기화에 실패 하였습니다.");
    }

  }

  /**
   *
   * 옵션값을 받아 초기화 한다.
   *
   * @Param options CrawlerExecuteOptions 객체
   * @Param seleniumExecuteNode SeleniumExecuteNode 객체
   */
  @Override
  public void init(CrawlerExecuteOptions options, SeleniumExecuteNode<T> seleniumExecuteNode) {
    try {
      // step1. 실행 노드 Set
      this.seleniumExecuteNode = seleniumExecuteNode;

      // Set Options
      DesiredCapabilities dc = new DesiredCapabilities();
      dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

      // step2. remote/local 구분 하여 selenium 초기화
      if(options.isRemote()) {
        if(options.isProxyUse()) {
          Proxy proxy = new Proxy();
          proxy.setHttpProxy(options.getProxyServerIp());
          dc.setCapability(CapabilityType.PROXY, proxy);
          dc.setBrowserName(BrowserType.CHROME);
          dc.setPlatform(Platform.ANY);
          this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER), dc);
        } else {
          this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER),
              dc.chrome());
        }
      } else {
        System.setProperty("webdriver.chrome.driver", CrawlerConstants.LOCALE_CHROME_DRIVER);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(dc);
        this.driver = new ChromeDriver(chromeOptions);
      }
    } catch (Exception e) {
      throw new RuntimeException("초기화에 실패 하였습니다.");
    }
  }



  /**
   *
   * 크롤링 Execute 로직을 실행한다.
   *
   * @return Type 객체
   */
  @Override
  public T execute() {
    try {
      return this.seleniumExecuteNode.getData(this.driver);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // 에러가 나도 최종적으로 크롤링 quit 처리
      this.driver.quit();
    }
  }

  /**
   *
   * Driver를 가져온다.
   *
   * @return
   */
  public WebDriver getDriver() {
    return this.driver;
  }
}
