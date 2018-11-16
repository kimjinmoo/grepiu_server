package com.grepiu.test.application.socket.mapper;

import java.util.List;
import org.assertj.core.util.Lists;
import org.json.JSONArray;

public class TypeBMap implements ObjectFieldMap {

  private String delimiter = "|";
  private String header;
  private List<SearchMapper> lists;

  public TypeBMap(String res) throws Exception {
    // 헤더 Set
    this.header = res.substring(0, res.indexOf(delimiter)+1);
    // 데이터 Set
    String d = res.substring(header.length(), res.length())+delimiter;

    lists = Lists.newArrayList();

    int pos = 0, end, index = 1, route = 3;
    SearchMapper j = null;

    while ((end = d.indexOf(delimiter, pos)) >= 0) {
      switch (index) {
        case 1 :
          j = new SearchMapper();
          j.setCode(d.substring(pos, end));
          break;
        case 2:
          j.setName(d.substring(pos, end));
          break;
        case 3:
          j.setCost(d.substring(pos, end));
          lists.add(j);
          index = 0;
          break;
      }
      index++;
      pos = end + 1;
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
