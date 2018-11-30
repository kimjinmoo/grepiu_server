package com.grepiu.www.process.sample.util.socket.module.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import java.util.HashMap;
import java.util.List;

public class FileDown extends SejongSocket {

  public FileDown(String code) {
    super(code);
  }

  @Override
  public List<SejongMap> send(SejongMap data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .toString();

    SejongMap returnMap = new SejongMap();
    List<SejongMap> files = Lists.newArrayList();

    returnMap.put("file", SocketHelper.getFile(sample.getBytes("KSC5601")));
    files.add(returnMap);

    return files;
  }
}
