package com.grepiu.www.process.common.tools.crawler.module;

import lombok.Builder;
import lombok.Data;

/**
 *
 *  크롤러 실행 옵션
 *
 */
@Builder
public class CrawlerExecuteOptions {
  @Builder.Default
  private boolean isProxyUse = false;     // 프럭시 사용 여부
  @Builder.Default
  private boolean isRemote = true;         // remote 서버 사용 여부

  private String proxyServerIp;            // 프럭시 사용인경우 해당 옵션 값이 필요 하다.

  public boolean isProxyUse() {
    return isProxyUse;
  }

  public String getProxyServerIp() {
    return proxyServerIp;
  }

  public boolean isRemote() {
    return isRemote;
  }
}
