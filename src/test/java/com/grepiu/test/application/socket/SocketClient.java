package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.GenerSearchBody;
import com.grepiu.www.process.sample.util.socket.module.model.GenerSearchVO;
import com.grepiu.www.process.sample.util.socket.module.model.GenreSearch;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * 소켓 테스트
 *
 */
public class SocketClient {

  private static int loop = 1;

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
      GenerSearchBody vo = new GenerSearchBody();
      vo.setBeginDate("20180101");
      vo.setEndDate("20180101");
      SejongSocket s = SejongFactory.create(TYPE.GENRE_SEARCH);
      s.send(vo);
      ((List<GenerSearchVO>) s.response()).stream().forEach(v->{
        System.out.println("d : " + v.getName() + " code : " + v.getCode());
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
