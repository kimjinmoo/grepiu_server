package com.grepiu.test.application.socket.module.model;

import com.grepiu.test.application.socket.module.utils.SocketUtils;

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
    String sample = sb.append(header)
        .append(data)
        .toString();

    System.out.println("watch : "+sample);
    return SocketUtils.sendDataStream(sample.getBytes("KSC5601"));
  }
}
