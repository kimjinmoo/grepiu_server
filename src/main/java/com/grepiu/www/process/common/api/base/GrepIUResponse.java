package com.grepiu.www.process.common.api.base;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;

public class GrepIUResponse {

  public Object ok(Object addObj) {
    LinkedHashMap map = Maps.newLinkedHashMap();
    map.put("code", HttpStatus.OK.value());
    map.put("data", addObj);

    return map;
  }
}
