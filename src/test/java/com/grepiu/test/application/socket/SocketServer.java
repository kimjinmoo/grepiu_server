package com.grepiu.test.application.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * 소켓 서버
 *
 */
public class SocketServer {

  public static void main(String...args) throws Exception {
    ServerSocket listener = new ServerSocket(9090);
    try {
      while (true) {
        Socket socket = listener.accept();
        try {
          DataInputStream in =
              new DataInputStream(socket.getInputStream());
          OutputStream out = new DataOutputStream(socket.getOutputStream());

          byte[] bytes = new byte[1024];
          int len = in.read(bytes);
          String result = new String(bytes, 0, len, "KSC5601");
          System.out.println("받음 : " + result);

          out.write(new Date().toString().getBytes("KSC5601"));
        } finally {
          socket.close();
        }
      }
    }
    finally {
      listener.close();
    }
  }
}
