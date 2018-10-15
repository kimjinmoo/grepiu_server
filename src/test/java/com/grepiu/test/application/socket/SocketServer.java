package com.grepiu.test.application.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.security.core.parameters.P;

public class SocketServer {

  public static void main(String...args) throws Exception {
    ServerSocket listener = new ServerSocket(9090);

    try {
      while (true) {
        Socket socket = listener.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("message received: " + message);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("메세지전달 " + message);
        ois.close();
        oos.close();
        socket.close();
        if (message.equalsIgnoreCase("exit"))
          break;
      }
    } finally {
      listener.close();
    }
  }
}
