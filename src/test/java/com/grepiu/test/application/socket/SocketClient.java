package com.grepiu.test.application.socket;

import com.google.common.collect.Lists;
import com.grepiu.test.application.socket.test.Header;
import com.grepiu.www.process.grepiu.domain.Post;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

public class SocketClient {

  public static void main(String...args) throws Exception {

//    Post post = new Post();
//    System.out.println(Optional.ofNullable(post).map(Post::getContent).orElse("N"));

    InetAddress host = InetAddress.getLocalHost();

    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    Header header = new Header("SAC!@#", "한글", "9101");

    while(true){
      // 소켓 초기화
      socket = new Socket(host.getHostName(), 9090);
      // 문자열 입력
      oos = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("send Message...");

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write(header.getDate());         // header
      outputStream.write("20180608".getBytes());  // body
      outputStream.write("20180609".getBytes());  // data
      oos.writeObject(outputStream.toByteArray());

      outputStream.close();

      oos.flush();
      // 응답값
//      ois = new DataInputStream(socket.getInputStream());
      // 종료
//      ois.close();
      oos.close();
      Thread.sleep(1000);
    }
  }

  private static String isN(String n) {
    String nn = "0000000"+n;
    return nn.substring(nn.length()-6,nn.length());
  }

}
