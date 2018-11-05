package com.grepiu.test.application.socket;

import com.grepiu.test.application.socket.module.SejongFactory;
import com.grepiu.test.application.socket.module.SejongFactory.TYPE;
import com.grepiu.test.application.socket.module.model.SejongSocket;

/**
 *
 * 모듈을 통한 전달
 *
 */
public class SocketModuleClient {

  public static void main(String...args) throws Exception {
    SejongSocket socket = SejongFactory.create(TYPE.GENRE_SEARCH);
    System.out.println("resp : " + socket.send("2018110520181106"));

    SejongSocket socket2 = SejongFactory.create(TYPE.WATCH_GRADE);
    System.out.println("resp : " + socket2.send("2018110520181106"));
  }
}
