package com.grepiu.www.process.common.tools.crawler.node;

import com.grepiu.www.process.common.tools.crawler.module.CrawlerExecuteOptions;
import org.openqa.selenium.WebDriver;

public interface SeleniumExecuteNode<T> {

  /**
   *
   * Selenium webDriver를 이용한 구현 로직 구현
   * 시작시 webDriver.get(url);
   *
   */
  public T getData(WebDriver webDriver);
}
