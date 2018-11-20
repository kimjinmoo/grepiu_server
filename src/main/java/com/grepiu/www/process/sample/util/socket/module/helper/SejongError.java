package com.grepiu.www.process.sample.util.socket.module.helper;

import java.util.Arrays;

public enum SejongError {
  ORDER_FAIL("0000","주문에 실패하였습니다."),
  ORDER_NETWORK_FAIL("0001","주문중 네트워크에 문제가 발생 하였습니다.");

  SejongError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  private String code;
  private String message;

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
