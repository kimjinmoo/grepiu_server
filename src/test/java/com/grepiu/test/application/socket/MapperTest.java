package com.grepiu.test.application.socket;

import com.grepiu.test.application.socket.mapper.SejongVO;

public class MapperTest {

  public static void main(String[] args) {
    String res = "SAC!@#0003179102026000010639102103                        103065                                                      0000N000014000000|없음|#000001|뮤지컬|#000003|콘서트|#000004|연극|#000006|가족/아동|#000007|오페라|#000009|전시/체험|#000013|국악|#000014|무용|#000024|클래식|#000999|기타|#001000|발레|#001001|교육|#001002|합창|#";

    SejongVO vo = new SejongVO(res);

    vo.getList().forEach(v->{
      System.out.println("v.getF : " + v.getField() + "v.getData() : " + v.getData());
    });
  }
}
