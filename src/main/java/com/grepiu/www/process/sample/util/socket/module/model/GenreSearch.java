package com.grepiu.www.process.sample.util.socket.module.model;

import com.google.common.collect.Maps;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Lists;

public class GenreSearch extends SejongSocket {

  public GenreSearch(String code) {
    super(code);
  }

  @Override
  public List<HashMap<String, String>> send(SejongMap data) throws Exception {

    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.get("beginDate"))
        .append(data.get("endDate"))
        .toString();
    this.response = SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
    return response();
  }

  private List<HashMap<String, String>> response() throws Exception {
    // 헤더 Set
    String responseheader = response.substring(0, response.indexOf(delimiter) + 1);
    // 데이터 Set
    String d = response.substring(header.length(), response.length()) + delimiter;

    List<HashMap<String, String>> data = Lists.newArrayList();

    int pos = 0, end, index = 1;
    HashMap<String,String> o = Maps.newLinkedHashMap();

    while ((end = d.indexOf(delimiter, pos)) >= 0) {
      if (index % 2 == 1) {
        if (index > 1) {
          data.add(o);
        }
        o = Maps.newHashMap();
        o.put("name", d.substring(pos, end));
      } else {
        o.put("code",d.substring(pos, end));
      }
      pos = end + 1;
      index++;
      if (pos > d.lastIndexOf(delimiter)) {
        data.add(o);
      }
    }
    return data;
  }
}
