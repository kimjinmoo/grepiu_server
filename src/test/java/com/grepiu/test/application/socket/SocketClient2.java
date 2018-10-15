package com.grepiu.test.application.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient2 {

  public static void main(String...args) throws Exception {
    InetAddress host = InetAddress.getLocalHost();

    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    while(true){
      // 소켓 초기화
      socket = new Socket(host.getHostName(), 9090);
      // 문자 입력
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject("몰라요");
      //응답 문자 처리
      ois = new ObjectInputStream(socket.getInputStream());
      String message = (String) ois.readObject();
      System.out.println("응답값 : " + message);
      // 종료
      ois.close();
      oos.close();
      Thread.sleep(1000);
    }
  }
}
