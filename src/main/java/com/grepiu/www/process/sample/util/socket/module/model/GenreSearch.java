package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GenreSearch<T extends GenerSearchBody> extends SejongSocket<T> {

  public GenreSearch(String code) {
    super(code);
  }

  @Override
  public String send(T data) throws Exception {

    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.getBeginDate())
        .append(data.getEndDate())
        .toString();
    return SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
  }
}
