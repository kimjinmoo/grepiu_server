package com.grepiu.www.process.sample.util.socket.module.exception;

public class ErrorCodeException extends Exception {
  private String code;
  private String message;

  public ErrorCodeException(String message) {
    super(message);
    this.message = message;
  }

  public String getCode(){
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
