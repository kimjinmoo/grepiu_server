package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;

/**
 *
 * 소켓 테스트
 *
 */
public class SocketClient {

  public static void main(String...args) throws Exception {
    // SocketServer 구동 후 확인 가능

    // 일반 검색
    System.out.println(SejongFactory.create(TYPE.GENRE_SEARCH).send("Hello"));

    // 등급 검색
    System.out.println(SejongFactory.create(TYPE.WATCH_GRADE).send("Hello"));
  }
}
