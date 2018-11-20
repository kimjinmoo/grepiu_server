package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.exception.SejongErrorCodeException;
import com.grepiu.www.process.sample.util.socket.module.helper.SejongCheckHelper;

public class ErrorCheck {

  public static void main(String[] args) {
    try {
      SejongCheckHelper.isSuccess("0001");
    } catch (SejongErrorCodeException e) {
      System.out.println(String.format(" code : %s, message : %s", e.getCode(), e.getMessage()));
    }

  }
}
