package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;

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
    return SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
  }
}
