package com.grepiu.test.application.socket;

import com.grepiu.test.application.socket.module.SejongFactory;
import com.grepiu.test.application.socket.module.SejongFactory.TYPE;
import com.grepiu.test.application.socket.module.model.SejongSocket;
import com.grepiu.test.application.socket.module.model.SocketConnection;
import java.util.Random;

/**
 *
 * 모듈을 통한 전달
 *
 */
public class SocketModuleClient {

  public static void main(String...args) throws Exception {

    for(int i = 0; i < 5000; i++) {
      new ThreadTest().start();
    }
  }
}

class ThreadTest extends Thread {
  public ThreadTest() {};

  public void run() {
    Random rand = new Random();
    try {
      sleep(rand.nextInt(1000)+1);
      SejongSocket socket = SejongFactory.create(TYPE.GENRE_SEARCH);
      System.out.println("resp : " + socket.send("2018110520181106"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
