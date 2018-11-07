package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.utils.SocketUtils;

public class GenreSearch extends SejongSocket {

  public GenreSearch(String code) {
    super(code);
  }

  @Override
  public String send(String data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data)
        .toString();
    System.out.println("gener : "+sample);
    return SocketUtils.sendDataStream(sample.getBytes("KSC5601"));
  }
}
