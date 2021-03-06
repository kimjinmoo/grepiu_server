package com.grepiu.www.process.sample.util.socket.module.pool;

import java.util.Optional;


public class ValidationResult {

  private String onErrorMsg = "";
  private boolean isSuccess = false;

  public ValidationResult(boolean isSuccess, String onErrorMsg) {
    this.onErrorMsg = onErrorMsg;
    this.isSuccess = isSuccess;
  }

  public String getOnErrorMsg() {
    return onErrorMsg;
  }

  public boolean isSuccess() {
    return isSuccess;
  }
}



