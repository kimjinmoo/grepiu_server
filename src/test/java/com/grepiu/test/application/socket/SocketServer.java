package com.grepiu.test.application.socket;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.security.core.parameters.P;

/**
 *
 * 소켓 서버
 *
 */
public class SocketServer {

  public static void main(String...args) throws Exception {
    ServerSocket listener = new ServerSocket(9090);
    Socket socket = null;

    String resMessage = "";
    try {
      while (true) {
        socket = listener.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        byte[] message = (byte[]) ois.readObject();
        System.out.println("message : " + new String(message,"KSC5601"));
//        String message = (String) ois.readObject();
//        if(message.length() == 10) {
//          System.out.println("정상적인 메세지: ");
//          System.out.println("성 : "+message.substring(0,1));
//          System.out.println("이름 : "+message.substring(1,3));
//          System.out.println("나머지글자 : "+message.substring(4, message.length()));
//          resMessage = "성공";
//        } else {
//          System.out.println("잘못된 메세지 " + message);
//          resMessage = "실패";
//        }

//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject("결과 메세지 : " + resMessage);
        ois.close();
//        oos.close();

      }
    } finally {
      socket.close();
      listener.close();
    }
  }
}
