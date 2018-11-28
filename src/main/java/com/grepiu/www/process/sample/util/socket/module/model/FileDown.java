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
  public List<HashMap<String, String>> send(SejongMap data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.get("path"))
        .toString();
    SocketHelper.getFile(sample.getBytes("KSC5601"),data.get("path").toString());
    List<HashMap<String, String>> files = Lists.newArrayList();
    HashMap<String, String> o = Maps.newHashMap();
    o.put("file", data.get("path").toString());
    files.add(o);
    return files;
  }
}
