package com.grepiu.test.application.socket;

import java.io.*;
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
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        byte[] in = new byte[100];
        int bytesRead = dis.read(in);
        resMessage += new String(in, 0, bytesRead);
        System.out.println("MESSAGE: " + resMessage);
//        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//        byte[] message = (byte[]) ois.readObject();
//        byte[] message = (byte[]) dis.rea;
//        System.out.println("message : " + new String(message,"KSC5601"));
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
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.write("서버는 데이터 받음".getBytes());
        dos.flush();
        dis.close();
        dos.close();
//        oos.close();

      }
    } finally {
      socket.close();
      listener.close();
    }
  }
}
