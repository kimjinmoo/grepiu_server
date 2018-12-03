package com.grepiu.www.process.sample.util.socket.module.model;

import java.util.Map;
import org.apache.commons.collections4.map.ListOrderedMap;

public class SejongMap extends ListOrderedMap {

  public void setGenerSearchSet(Object beginDate, Object endDate){
    super.put("beginDate", beginDate);
    super.put("endDate", endDate);
  }

  public void setWatchGrade(Object type, Object cost) {
    super.put("type", type);
    super.put("cost", cost);
  }

  public byte[] getFile() {
    return (byte[]) this.get("file");
  }

  public int getInt(String key) {
    if(this.containsKey(key)) {
      return (int) this.get(key);
    } else {
      return 0;
    }
  }

  public String getString(String key) {
    if(this.containsKey(key)) {
      return (String) this.get(key);
    } else {
      return null;
    }
  }
}
