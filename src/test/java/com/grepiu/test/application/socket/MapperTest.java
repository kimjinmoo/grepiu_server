package com.grepiu.test.application.socket;

import com.google.gson.Gson;
import com.grepiu.test.application.socket.mapper.OrderMapper;
import com.grepiu.test.application.socket.mapper.SearchMapper;
import com.grepiu.test.application.socket.mapper.SejongVO;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class MapperTest {

  public static void main(String[] args) throws Exception {
    String res1 = "SAC!@#0003179102026000010639102103                        103065                                                      0000N000014000000|없음|#000001|뮤지컬|#000003|콘서트|#000004|연극|#000006|가족/아동|#000007|오페라|#000009|전시/체험|#000013|국악|#000014|무용|#000024|클래식|#000999|기타|#001000|발레|#001001|교육|#001002|합창|#";
    String res2 = "SAC!@#0003179102026000010639102103                        103065                                                      0000N000014000000|없음|#000001|5000|뮤지컬|#00002|6000";

    new SejongVO(TYPE.WATCH_GRADE, res1).getOrderMapper().stream().forEach(v->{
      System.out.println("code : " + v.getCode() + " name : " + v.getName());
    });

    new SejongVO(TYPE.GENRE_SEARCH, res2).getSearchMapper().stream().forEach(v->{
      System.out.println("code : " + v.getCode() + " name : " + v.getName() + " cost : " + v.getCost());
    });

  }
}
