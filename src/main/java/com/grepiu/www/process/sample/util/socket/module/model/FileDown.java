package com.grepiu.www.process.sample.util.socket.module.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import java.util.HashMap;
import java.util.List;

public class FileDown extends SejongSocket {

  private final String host = "52.78.158.161";
  private final int port = 9080;

  public FileDown(String code) {
    super(code);
  }

  @Override
  public List<SejongMap> send(SejongMap data) throws Exception {
    SejongMap returnMap = new SejongMap();
    List<SejongMap> files = Lists.newArrayList();

    returnMap.put("file", SocketHelper.getFile(host, port, data.getString("file").getBytes()));

    files.add(returnMap);

    return files;
  }
}
