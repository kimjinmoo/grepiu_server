package com.grepiu.www.process.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevUtils {

  private long startTime = 0;
  private long endTime =0;

  public void begin() {
    this.startTime = System.nanoTime();
  }

  public void stop() {
    this.endTime = System.nanoTime();
  }

  public void summary() {
    log.info("execute time : {}", this.endTime - this.startTime);
  }

}
