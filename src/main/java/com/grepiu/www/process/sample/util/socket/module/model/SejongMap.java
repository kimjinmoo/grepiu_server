package com.grepiu.www.process.sample.util.socket.module.model;

import java.util.Map;
import org.apache.commons.collections4.map.ListOrderedMap;

public class SejongMap extends ListOrderedMap {

  public void setGenerSearchSet(Object beginDate, Object endDate){
    super.put("beginDate", beginDate);
    super.put("endDate", endDate);
  }
}
