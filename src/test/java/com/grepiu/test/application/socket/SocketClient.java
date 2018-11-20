package com.grepiu.test.application.socket;

import com.google.common.collect.Maps;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.domain.GenerSearchBody;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;

import java.util.HashMap;
import java.util.List;
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
      HashMap<String, String> vo = Maps.newHashMap();
      vo.put("beginDate","20180101");
      vo.put("endDate","20180101");
      SejongSocket s = SejongFactory.create(TYPE.GENRE_SEARCH);
      s.send(vo);
      s.response().stream().forEach(v->{
        System.out.println("name : "+ v.get("name") + "code : " + v.get("code"));
      });
      SejongSocket s2 = SejongFactory.create(TYPE.WATCH_GRADE);
      s.send(vo);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
