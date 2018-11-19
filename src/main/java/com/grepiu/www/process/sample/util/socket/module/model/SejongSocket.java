package com.grepiu.www.process.sample.util.socket.module.model;

/**
 *
 * 세종 소켓
 *
 */
public abstract class SejongSocket<T extends SejongBody> {

  // 헤더
  protected String header;

  public SejongSocket(String code) {
    StringBuilder sb = new StringBuilder();
    this.header = sb.append("SAC!@#")
        .append("   133")
        .append(code)
        .append("v9.9.9.9    ")
        .append("102103                        ")
        .append("103065                        ")
        .append("N")
        .append("                             ")
        .append("    ")
        .append(" ")
        .append("000000").toString();
  }

  // 메세지를 전송한다.
  public abstract String send(T obj) throws Exception;
}
