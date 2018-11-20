package com.grepiu.www.process.sample.util.socket.module.model;

import com.google.common.collect.Maps;
import com.grepiu.www.process.sample.util.socket.module.helper.SejongCheckHelper;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import java.util.HashMap;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 관람 등급
 */
public class WatchGrade extends SejongSocket {

  public WatchGrade(String code) {
    super(code);
  }

  @Override
  public List<HashMap<String, String>> send(SejongMap data) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.get("cost"))
        .append(data.get("type"))
        .toString();
    this.response = SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
    return response();
  }

  private List<HashMap<String, String>> response() throws Exception {
    // 헤더 Success/Fail 체크
    SejongCheckHelper.isSuccess(response.substring(0, response.indexOf(delimiter) + 1));
    // 데이터 Set
    String d = response.substring(header.length(), response.length()) + delimiter;


    List<HashMap<String, String>> data = Lists.newArrayList();

    int pos = 0, end, index = 1, route = 3;
    HashMap<String, String> o = Maps.newHashMap();

    while ((end = d.indexOf(delimiter, pos)) >= 0) {
      switch (index) {
        case 1:
          o = Maps.newHashMap();
          o.put("code", d.substring(pos, end));
          break;
        case 2:
          o.put("name", d.substring(pos, end));
          break;
        case 3:
          o.put("cost", d.substring(pos, end));
          data.add(o);
          index = 0;
          break;
      }
      index++;
      pos = end + 1;
    }
    return data;
  }
}
