package com.grepiu.www.process.sample.util.socket.module.exception;

public class SejongErrorCodeException extends Exception {
  private String code;
  private String message;

  public SejongErrorCodeException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode(){
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
