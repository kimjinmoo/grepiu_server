package com.grepiu.test.process;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Data;

public class MapToString {


  public static void main(String...arsg) {
    // 앞자리 고정 5자리/ 루프 2,3,4
    String message = new StringBuilder().append("00200")
        .append("00")
        .append("300")
        .append("5343")
        .append("01")
        .append("400")
        .append("6555").toString();

    List<LinkedHashMap<String, Object>> m = Lists.newArrayList();

    final int fixedDateLength = 5;
    final int maxLength = 9;
    message = message.substring(5, message.length());
    while(message.length() >= maxLength) {
      LinkedHashMap<String, Object> o = Maps.newLinkedHashMap();
      o.put("code1", message.substring(0,2));
      o.put("code2", message.substring(2,5));
      o.put("code3", message.substring(5,maxLength));
      message = message.substring(maxLength, message.length());
      m.add(o);
    }


    Function<Map, String> a = new Function<Map, String>() {
      @Override
      public String apply(Map map) {
        return null;
      }
    };
    m.stream().forEach((v)->{
      System.out.println("code1 : " + v.get("code1"));
      System.out.println("code2 : " + v.get("code2"));
      System.out.println("code3 : " + v.get("code3"));
    });
  }
}
@Data
class VO {
  String code;
  String phone;

  public VO(String code, String phone) {
    this.code = code;
    this.phone = phone;
  }
}
