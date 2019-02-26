package com.grepiu.www.process.common.tools.crawler.module;

import com.grepiu.www.process.common.tools.crawler.node.SeleniumExecuteNode;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
      //step2. driver 초기화
      this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER),
          DesiredCapabilities.chrome());
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

      // step2. remote/local 구분 하여 selenium 초기화
      if(options.isRemote()) {
        if(options.isProxyUse()) {
          Proxy proxy = new Proxy();
          proxy.setHttpProxy(options.getProxyServerIp());
          DesiredCapabilities cap = new DesiredCapabilities();
          cap.setCapability(CapabilityType.PROXY, proxy);
          cap.setBrowserName(BrowserType.CHROME);
          cap.setPlatform(Platform.ANY);
          this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER), cap);
        } else {
          this.driver = new RemoteWebDriver(new URL(CrawlerConstants.REMOTE_CHROME_DRIVER),
              DesiredCapabilities.chrome());
        }
      } else {
        System.setProperty("webdriver.chrome.driver", CrawlerConstants.LOCALE_CHROME_DRIVER);
        this.driver = new ChromeDriver();
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
    return this.seleniumExecuteNode.getData(this.driver);
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
