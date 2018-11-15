package com.grepiu.test.application.socket.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 공통 VO
 *
 */
public class SejongVO {
  private String delimiter = "|";
  private String header;
  private String data;
  private List<ObjectFieldMap> list;

  public SejongVO(String res) {
    // validation
    if (res.length() > 0 && res.lastIndexOf(delimiter) > 0) {
      // 리스트 초기화
      list = new ArrayList<>();
      // 헤더 Set
      this.header = res.substring(0, res.indexOf(delimiter)+1);
      // 데이터 Set
      this.data = res.substring(header.length(), res.length())+delimiter;

      int pos = 0, end, index = 1;

      ObjectFieldMap objectFieldMap = null;
      while ((end = this.data.indexOf(delimiter, pos)) >= 0) {
        if(index%2 == 1) {
          if(index > 1){
            list.add(objectFieldMap);
          }
          objectFieldMap = new ObjectFieldMap();
          objectFieldMap.setField(this.data.substring(pos, end));
        } else {
          objectFieldMap.setData(this.data.substring(pos, end));
        }
        pos = end + 1;
        index++;
        if(pos > this.data.lastIndexOf(delimiter)) list.add(objectFieldMap);
      }
    } else {
      throw new RuntimeException("잘못된 String 값");
    }
  }

  public String getHeader() {
    return header;
  }

  public List<ObjectFieldMap> getList() {
    return list;
  }
}
