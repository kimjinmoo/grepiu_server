package com.grepiu.test.application.socket.mapper;

import java.util.List;
import org.assertj.core.util.Lists;
import org.json.JSONArray;
import org.json.JSONObject;

public class TypeAMap implements ObjectFieldMap {

  private String delimiter = "|";
  private String header;
  private List<OrderMapper> lists;

  public TypeAMap(String res) throws Exception {
    // 헤더 Set
    this.header = res.substring(0, res.indexOf(delimiter)+1);
    // 데이터 Set
    String d = res.substring(header.length(), res.length())+delimiter;

    lists = Lists.newArrayList();

    int pos = 0, end, index = 1;
    OrderMapper j = null;

    while ((end = d.indexOf(delimiter, pos)) >= 0) {
      if(index%2 == 1) {
        if(index > 1){
          lists.add(j);
        }
        j = new OrderMapper();
        j.setName(d.substring(pos, end));
      } else {
        j.setCode(d.substring(pos, end));
      }
      pos = end + 1;
      index++;
      if(pos > d.lastIndexOf(delimiter)) lists.add(j);
    }
  }

  @Override
  public String getHeader() {
    return this.header;
  }

  @Override
  public Object getData() {
    return this.lists;
  }
}
