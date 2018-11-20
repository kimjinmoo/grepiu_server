package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import java.util.Random;

/**
 *
 * 소켓 테스트
 *
 */
public class SocketClient {

  private static int loop = 50;

  public static void main(String...args) throws Exception {
    //
    for(int i = 0 ; i < loop; i++) {
      Thread thread = new Thread(new ThreadTest());
      thread.start();
    }
  }
}

class ThreadTest implements Runnable {

  @Override
  public void run() {
    try {
      // 일반 검색
      Random random = new Random();
      Thread.sleep(random.nextInt(1000)+1);
      if(random.nextInt(10)%2==0){
        SejongMap genre = new SejongMap();
        genre.setGenerSearchSet("20181101","20180101");
        SejongSocket s = SejongFactory.create(TYPE.GENRE_SEARCH);
        s.send(genre).stream().forEach(v->{
          System.out.println("name : "+ v.get("name") + "code : " + v.get("code"));
        });
      } else {
        SejongMap watch = new SejongMap();
        SejongSocket s2 = SejongFactory.create(TYPE.WATCH_GRADE);
        s2.send(watch).stream().forEach(v->{
          System.out.println("name : "+ v.get("name") + "code : " + v.get("code"));
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
