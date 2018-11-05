package com.grepiu.test.application.socket.module.model;

import com.grepiu.www.process.common.utils.SocketUtils;
import java.net.Socket;

/**
 *
 * 관람 등급
 *
 */
public class WatchGrade extends SejongSocket {

  public WatchGrade(String code) {
    super(code);
  }

  @Override
  public String send(String data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append("SAC!@#")
        .append("   133")
        .append(super.code)
        .append("v9.9.9.9    ")
        .append("102103                        ")
        .append("103065                        ")
        .append("N")
        .append("                             ")
        .append("    ")
        .append(" ")
        .append("000000")
        .append(data)
        .toString();

    System.out.println("watch : "+sample);
    return SocketUtils.sendDataStream(new Socket(), ip, port, sample.getBytes("KSC5601"));
  }
}
