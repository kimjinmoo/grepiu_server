package com.grepiu.www.process.common.tools.crawler.node;

import lombok.Builder;
import lombok.Data;

@Builder
public class ExecuteOption {
  private boolean isProxyUse = false;
  private String proxyServerIp;

  public boolean isProxyUse() {
    return isProxyUse;
  }

  public String getProxyServerIp() {
    return proxyServerIp;
  }
}
