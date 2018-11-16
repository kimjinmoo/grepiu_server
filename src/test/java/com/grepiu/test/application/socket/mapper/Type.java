package com.grepiu.test.application.socket.mapper;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.assertj.core.util.Sets;

public class Type {

  HashMap<String, ObjectFieldMap> o = Maps.newHashMap();

  public void setType(ObjectFieldMap objectFieldMap){
    o.put("data", objectFieldMap);
  }

  public ObjectFieldMap getType() {
    return o.containsKey("data") ? o.get("data") : null;
  }
}
