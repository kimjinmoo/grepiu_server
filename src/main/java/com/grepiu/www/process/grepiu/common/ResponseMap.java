package com.grepiu.www.process.grepiu.common;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

@Builder
public class ResponseMap {
  private int code;
  private HashMap<Object, Object> data;
}
