package com.grepiu.test.application.socket;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import org.assertj.core.util.Lists;

public class Pasing {

  public static void main(String...args) {
    String d= "102103|10000491|T_시크릿가든|000024|120|Y|#102103|10001339|(테스트)T_카카오_S씨어터|000004|120|Y|#102103|10001342|테스트 <공연> - 신청|000000||Y|#102103|10000544|T_양재웅 피아노 독주회|000024|90|Y|#102103|10000612|T_2016서울스프링실내악축제<Grandioso>|000024|100|Y|#102103|10001275|(테스트)T_디에고리베라 프라이드 오브 멕시코|000009||Y|#102103|10001307|T_뮤지컬 갈라 콘서트|000001|120|Y|#102103|10000775|T_서울콘서트필하모닉오케스트라와 함께하는 송년음악회|000024|105|Y|#102103|10001119|T_박초희 귀국 피아노 독주회(테스트)|000024|90|Y|#102103|10001332|(T테스트) 공연등록테스트|000000|120|Y|#102103|10001341|(테스트)T_카카오_미술관|000009||Y|#102103|10001344|(테스트)T_S씨어터_가변1형|000000|120|Y|#102103|10000911|T_2017 세종문화회관 신년음악회|000024|100|Y|#102103|10001335|테스트 <공연> - 신청|000000||Y|#102103|10001343|테스트 <공연> - 신청|000000||Y|#102103|10000751|T_메조소프라노 변지현 귀국독창회|000024|90|Y|#102103|10000722|T_초콜릿 콘서트_세상에서 가장 아름다운 발렌타인데이|000024||Y|#102103|10000948|T_THE MOST Ensemble-Quintet 20|000024|90|Y|#102103|10001330|테스트_20181229|000000||Y|#102103|10001338|(테스트)T_카카오_대극장|000001|120|Y|#102103|10001327|T_(테스트)11번가_미술관|000009||Y|#102103|10001336";
    String delimiter1 = "#";
    String delimiter2 = "|";
    List<HashMap<String, String>> data = Lists.newArrayList();

    int pos = 0, end = 1;
    HashMap<String, String> o = Maps.newHashMap();

    while((end = d.indexOf(delimiter1, pos)) >= 0) {
      String object = d.substring(pos, end);
      System.out.println(object);
      int pos2 = 0, end2 = 1, index = 1;
      while ((end2 = object.indexOf(delimiter2, pos2)) >= 0) {
        System.out.println("str : " + object.substring(pos2, end2) + ", index : " + index);
        switch (index) {
          case 1:
            o = Maps.newHashMap();
            o.put("type1", object.substring(pos2, end2));
            break;
          case 2:
            o.put("type2", object.substring(pos2, end2));
            break;
          case 3:
            o.put("type3", object.substring(pos2, end2));
            break;
          case 4:
            o.put("type4", object.substring(pos2, end2));
            break;
          case 5:
            o.put("type5", object.substring(pos2, end2));
            break;
          case 6 :
            o.put("type6", object.substring(pos2, end2));
            data.add(o);
            index = 0;
            break;
        }
        index++;
        pos2 = end2 + 1;
      }
      pos = end + 1;
    }

    data.stream().forEach(v->{
      System.out.println(String.format("type 1 %s, type2 %s type3 %s type4 %s type5 %s type6 %s", v.get("type1"),v.get("type2"),v.get("type3"),v.get("type4"),v.get("type5"), v.get("type6")));
    });
  }
}
