package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.FileDown;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import java.io.File;
import java.util.Random;
import org.apache.commons.io.FileUtils;

/**
 *
 * 소켓 테스트
 *
 */
public class SocketClient {

  private static int loop = 1000;

  public static void main(String...args) throws Exception {
    //
    int number = 0;
    for(int i = 0 ; i < loop; i++) {
      Thread thread = new Thread(new FileTest(i));
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
class FileTest implements Runnable {

  private int number = 0;

  public FileTest(int number) {
    this.number = number;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    try {
      // 일반 검색
      SejongMap genre = new SejongMap();
      SejongSocket s = SejongFactory.create(TYPE.FILE_DOWN);
      s.send(genre).stream().forEach(v->{
        try {
          FileUtils.writeByteArrayToFile(new File("/data/test_" + this.number+".txt"), v.getFile());
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
