package com.grepiu.www.process.common.tools.crawler.node;

import com.grepiu.www.process.common.tools.crawler.module.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SampleNode implements SeleniumExecuteNode<String> {

  /**
   * Selenium webDriver를 이용한 구현 로직 구현
   */
  @Override
  public String execute(WebDriver webDriver) {
    webDriver.get("https://www.google.com");
    webDriver.findElement(By.name("q")).sendKeys("google");
    SeleniumUtils.elementClick(webDriver, webDriver.findElement(By.name("btnK")));
    String result = webDriver.findElement(By.className("srg")).getText();
    webDriver.quit();
    return result;
  }
}
