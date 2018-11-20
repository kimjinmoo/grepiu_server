package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.domain.SejongBody;
import java.util.List;

/**
 * 세종 소켓
 */
public abstract class SejongSocket<T1 extends SejongBody, T2> {

  // 헤더
  protected String header;
  protected String response;
  protected final String delimiter = "|";
  protected List<T2> data;

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
  public abstract void send(T1 obj) throws Exception;

  public abstract List<T2> response(Class<T2> type) throws Exception;
}
