package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;

/**
 *
 * 관람 등급
 *
 */
public class WatchGrade<T extends WatchGradeBody> extends SejongSocket<T> {

  public WatchGrade(String code) {
    super(code);
  }

  @Override
  public String send(T data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.getCost())
        .append(data.getType())
        .toString();
    return SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
  }
}
