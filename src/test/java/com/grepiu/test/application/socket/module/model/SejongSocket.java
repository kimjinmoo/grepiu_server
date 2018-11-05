package com.grepiu.test.application.socket.module.model;

/**
 *
 * 세종 소켓
 *
 */
public abstract class SejongSocket {

  // 전문 코드
  protected String code;

  // 세종문화 IP
  protected String ip = "127.0.0.1";

  // 세종문화 Port
  protected int port = 9090;

  public SejongSocket(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  // 메세지를 전송한다.
  public abstract String send(String data) throws Exception;
}
